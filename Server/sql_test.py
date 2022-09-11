
# import pandas as pd
# # namelist = request.form['namelist']
# tablename = "CT20B1"
# data = pd.read_csv(r"C:\Users\praka\Downloads\batch 1.csv")
# namelist = data.to_dict()
# rollnum = []
# name = []
# createQuery = f"create table {tablename}(c_date varchar(10),"
# for i in range(len(namelist)-1):
#     createQuery+="%s varchar(1),"
#     rollnum.append()
# createQuery=createQuery[:-1]
# createQuery+=")"
# print(createQuery)
from finalML.cropper import cropper
cropper()