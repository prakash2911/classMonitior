def cropper():
    import os
    import cv2
    from finalML.ml import deepFace
    # Read the input image
    #print(pics)
    c = 1
    import os
    pics = os.listdir('finalML/frames/')
    for i in pics:
        source = "finalML/frames/"
        source += i
        img = cv2.imread(source)
        print(source,img)

        #print(img)
        # Convert into grayscale
        gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        
        # Load the cascade
        face_cascade = cv2.CascadeClassifier('finalML/haarcascade_frontalface_alt2.xml')
        
        # Detect faces
        faces = face_cascade.detectMultiScale(gray, 1.1, 4)
        
        path = "finalML/testingpics"
        # Draw rectangle around the faces and crop the faces
        for (x, y, w, h) in faces:
            picName = "face"
            picName += str(c)
            picName += ".jpg"
            c += 1
            #cv2.rectangle(img, (x, y), (x+w, y+h), (0, 0, 255), 2)
            faces = img[y-500:y + h+500, x-500:x + w+500]
            print(picName)
            cv2.imwrite(os.path.join(path ,picName), faces)
    deepFace(c)
