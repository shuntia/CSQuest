from PIL import Image
import os

def process_image(image_path, out_path):
    bg_color = (0x8A, 0x5A, 0x9D)    # Background color (138, 90, 157)
    rect_color = (0xC3, 0x86, 0xFF)  # Rectangle color (195, 134, 255)

    # Load the image
    img = Image.open(image_path).convert('RGBA')
    pixels = img.load()
    width, height = img.size

    visited = [[False for _ in range(width)] for _ in range(height)]
    y = 0
    sprite_index = 0

    while y < height:
        x = 0
        row_height = 1
        while x < width:
            if visited[y][x] or pixels[x, y][:3] == bg_color:
                x += 1
            elif pixels[x, y][:3] == rect_color:
                x0, y0 = x, y

                # Expand horizontally until hitting bg_color
                x1 = x0
                while x1 < width and pixels[x1, y0][:3] != bg_color:
                    x1 += 1

                # Expand vertically until hitting bg_color
                y1 = y0
                while y1 < height and pixels[x0, y1][:3] != bg_color:
                    y1 += 1

                # Mark visited pixels
                for yy in range(y0, y1):
                    for xx in range(x0, x1):
                        visited[yy][xx] = True

                # Crop and save the rectangle
                rect = (x0, y0, x1, y1)
                cropped = img.crop(rect)
                cropped_pixels = cropped.load()
                w, h = cropped.size

                # Change rect_color to transparent
                for j in range(h):
                    for i in range(w):
                        if cropped_pixels[i, j][:3] == rect_color:
                            cropped_pixels[i, j] = (0, 0, 0, 0)

                os.makedirs(out_path, exist_ok=True)
                cropped.save(os.path.join(out_path, f'sprite_{sprite_index}.png'))
                sprite_index += 1

                # Update row height
                rect_height = y1 - y0
                row_height = max(row_height, rect_height)

                x = x1
            else:
                visited[y][x] = True
                x += 1
        y += row_height

    # Remove background color
    for y in range(height):
        for x in range(width):
            if pixels[x, y][:3] == bg_color:
                pixels[x, y] = (0, 0, 0, 0)

# Example usage
process_image('sprites/Frisk/PC Computer - Undertale - Frisk.png', 'sprites/Frisk')