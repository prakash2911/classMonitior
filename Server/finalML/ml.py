def deepFace(c):
    from deepface import DeepFace
    attendance = set()
    for i in range(1,c):
        model1 = DeepFace.build_model("VGG-Face")
        picpath = "testingpics/face"
        picpath += str(i)
        picpath += ".jpg"
        print(picpath)
        try:
            df = DeepFace.find(img_path = picpath, db_path = "training",distance_metric = "euclidean_l2",model=model1,model_name = "VGG-Face")
        #print(df)
        except:
            print("hi")
            continue
        if(df.size > 0):
            x=df.to_numpy()
            print(x)
            l=x[0][0]
            y = x[0][1]
            print(y)
            if(y>0.85):
                l = "none"
                print("none")
                # return l
            else:
                l=l.split("/")
                print(l)
                l=l[0]
                l=l.split("\\")
                l=l[1]
                print(l)
                attendance.add(l)
            # return l;
        else:
            print("none")

    print("\n\nATTENDANCE:\n")
    l = list(attendance)
    for i in l:
        print(i)
    import os
 
    # dir = 'frames'
    # for f in os.listdir(dir):
    #     os.remove(os.path.join(dir, f))
    # dir = 'testingpics'
    # for f in os.listdir(dir):
    #     os.remove(os.path.join(dir, f))
        # return "none"
# def deepFace():
#     from deepface import DeepFace
#     model1 = DeepFace.build_model("VGG-Face")
#     picpath = "E:/Face Recognition CNN/face1.jpg"
#     #picpath += "2.jpg"
#     df = DeepFace.find(img_path = '', db_path = "./Trainset",distance_metric = "euclidean_l2",model=model1,model_name = "VGG-Face")
#     print(df)
    
#     if(df.size > 0):
#             x=df.to_numpy()
#             print(x)
#             l=x[0][0]
#             y = x[0][1]
#             print(y)
#             if(y>0.65):
#                 l = "none"
#                 print("none")
#                 # return l
#             else:
#                 l=l.split("/")
#                 print(l)
#                 l=l[1]
#                 l=l.split("\\")
#                 l=l[1]
#                 print(l)
#             # return l;
#     else:
#         print("none")
#         # return "none"
# deepFace()