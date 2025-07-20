import os
import sys
import cv2
import easyocr
import cvzone

# Suppress stderr (e.g., CUDA messages)
sys.stderr = open(os.devnull, 'w')
os.environ["CUDA_VISIBLE_DEVICES"] = ""

# Global EasyOCR reader
reader = easyocr.Reader(["en"], gpu=False)

def text_position(arr, space):
    sorted_text = []

    for i, (text1, x1, y1) in enumerate(arr):
        is_valid = True

        for j, (text2, x2, y2) in enumerate(arr):
            if i != j and text2 not in sorted_text:
                if not (y1 <= y2 + 10 and x1 - (space * 3) <= x2):
                    is_valid = False
                    break

        if is_valid:
            sorted_text.append(text1)

    for text, _, _ in arr:
        if text not in sorted_text:
            sorted_text.append(text)

    return " ".join(sorted_text)

def wrap_text_to_fit(text, font, font_scale, thickness, max_width):
    words, lines, line = text.split(), [], ""
    for word in words:
        test_line = f"{line} {word}".strip()
        if cv2.getTextSize(test_line, font, font_scale, thickness)[0][0] <= max_width:
            line = test_line
        else:
            lines.append(line)
            line = word
    if line:
        lines.append(line)
    return lines


def easyocr_find(image_path):
    reader = easyocr.Reader(["en"])
    result = reader.readtext(image_path)
    image = cv2.imread(image_path)
    if result:
        unfbboxes = []
        fbboxes = []
        mainboxes = []
        text_list = []

        for (bbox, text, _) in result:
            p1, p2, p3, p4 = bbox
            x1, y1 = map(int, p1)
            x2, y2 = map(int, p2)
            x3, y3 = map(int, p3)
            x4, y4 = map(int, p4)

            area = (x2 - x1) * (y3 - y2)
            width = x3 - x1
            height = y3 - y1

            unfbboxes.append((bbox, text, area, x1, y1, x3, y3, width, height))

            if "tablet" in text.lower() or "capsules" in text.lower():
                sizelen = len(text)
                mainboxes.append((bbox, text, area, x1, y1, x3, y3, width, height, sizelen))

        if not mainboxes:
            return "nil"


        mainboxes.sort(key=lambda x: x[2], reverse=True)
        mx1, my1, mx3, my3, mw, mh, sizelen = mainboxes[0][3:10]
        space = mw // sizelen
        varm, text_list = [[mx1, my1, mx3, my3]], [[mainboxes[0][1], mx1, my1]]
        x_coordinates, y_coordinates, lst_text = [], [], []

        index = 0
        while index < len(varm):
            varx1, vary1, varx3, vary3 = varm[index]
            for i in unfbboxes:
                bbox, text, area, x1, y1, x3, y3, width, height = i
                if vary1 - mh - (space * 1.3) <= y1 and vary1 + mh + (space * 1.3) >= y3 and height >= mh - (space * 1.3) and height <= mh + (space * 1.3):
                        if x1 >= varx1-(space * 3) and x1 <= varx3 + (space * 1.5):
                            if vary1-5 <= y3 :
                                if x1 <= varx3+5:
                                    if not "€" in text:
                                            fbboxes.append(i)
                                            x_coordinates.extend([x1, x3])
                                            y_coordinates.extend([y1, y3])
                                            lst_text.append([text, x1, y1])
                                            if varx3 <= x3 and [x1, y1, x3, y3] not in varm:
                                                varm.append([x1, y1, x3, y3])
            index += 1


        unique_nested_list = sorted({tuple(t) for t in lst_text}, key=lambda x: x[2])
        min_x, max_x, min_y, max_y = min(x_coordinates), max(x_coordinates), min(y_coordinates), max(y_coordinates)
        # cvzone.cornerRect(image, (min_x, min_y, max_x - min_x, max_y - min_y), colorR=(255, 0, 0), l=20, rt=1, t=5)
        text = text_position(unique_nested_list, space)
        # print(f"Medicine: {text}")
        # print(f"Detected Text from {os.path.basename(image_path)}: {text_position(unique_nested_list, space)}")

        wrapped_text = wrap_text_to_fit(text, cv2.FONT_HERSHEY_SIMPLEX, 0.5, 2, max_x - min_x)
        y_offset = min_y - 10 - 40 * (len(wrapped_text) - 1)

        for line in wrapped_text:
            cvzone.cornerRect(image, (min_x, min_y, max_x - min_x, max_y - min_y), colorR=(0, 255, 0), l=20, rt=2)

            cvzone.putTextRect(image, line, (min_x + 10, y_offset), scale=1, thickness=2,
                                colorR=(255, 0, 0), colorT=(255, 255, 255),
                                font=cv2.FONT_HERSHEY_SIMPLEX, offset=10)
            y_offset += 40

        # Show the labeled image
        cv2.imshow("Labeled Image", image)
        cv2.waitKey(0)
        cv2.destroyAllWindows()

        return text
    else:
        return "nil"

### NOTE_START: Main script entry point (called from Java or terminal)
if __name__ == "__main__":
    image_path = sys.argv[1]  # get image path argument
    result = easyocr_find(image_path)
    print(result)
### NOTE_END