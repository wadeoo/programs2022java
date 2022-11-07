from PIL import Image
import os
def img_resize(file,w,h):
    img= Image.open(file)
    Resized=img.resize((w,h),Image.ANTIALIAS)
    Resized.save('resized.jpg','JPEG',quality=90)

img_resize("./1.jpg",100,75)
