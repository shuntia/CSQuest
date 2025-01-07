import os
from PIL import Image

def process_image(image_path, out_path):
    bg_color = (0xC3,0x86,0xFF)  # Background color (138, 90, 157)
    
    if os.path.isfile(image_path):
        files = [image_path]
    else:
        files = [os.path.join(image_path, f) for f in os.listdir(image_path) if os.path.isfile(os.path.join(image_path, f))]
    empty=True
    for f in files:
        image = image = Image.open(f).convert('RGBA')
        width, height = image.size
        pixels = image.load()
        
        for x in range(width):
            for y in range(height):
                if pixels[x, y][:3] == bg_color:
                    pixels[x, y] = (0, 0, 0, 0)
                else:
                    empty=False
        
        os.makedirs(out_path, exist_ok=True)
        out_file_path = os.path.join(out_path, os.path.basename(f))
        if not empty:image.save(out_file_path)

process_image(input(), input())