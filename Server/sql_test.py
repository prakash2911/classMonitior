from re import S
from select import select
import mysql.connector
import json

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="14072003jp",
    database="test"
)

cursor = mydb.cursor()

#query ="Create table sample (name varchar(255), pass varchar(255))"
#values =("sample",)
#cursor.execute(query)
# insert = "Insert into sample values(%s,%s)"
# values=["guru","root"]
# cursor.execute(insert,values)
# mydb.commit()

# select = 'select * from sample where name = %s and pass = %s'
# name = "gu"
# passw = "root"
# val=[name,passw]
# cursor.execute(select,val)

class_name ="ct_2020_batch1"
query = f"select * from {class_name}"
cursor.execute(query)       
detail = cursor.fetchall()
cursor.reset()

details ={detail[i][0]:detail[i][1] for i in range(0,len(detail))}


query_="create table CT_2020_BATCH1_ATT( c_date date, "
for i in detail:
    query_=query_+"_"+i[0]+" varchar(1),"
query_+=query_[:-1]
query_ = query_+")"
print(query_)
cursor.execute(query_)

# insert ="insert into ct_2020_batch1_att values(curdate(),"
# s="%s,"
# val=[]
# for i in range(len(detail)):
#     insert+=s 
#     val.append("1")
# insert=insert[:-1]
# insert+=")"

# print(insert)
# print(val)
# cursor.execute(insert,val)
# mydb.commit()

# att={"1":0,"2":1}
# li=[]
# for i in att:
#     li.append(att[i])
# print(li)







