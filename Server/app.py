# from unicodedata import name
import base64
from pydoc import classname
from select import select
import pandas as pd
from flask import *
import json
# from select import select
import mysql.connector
import werkzeug
# import os
import io
import cv2
from PIL import Image
# from PIL import ImageEnhance
import uuid
from finalML.cropper import cropper
ALLOWED_EXTENSIONS={'jpeg','png','jpeg'}
mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="14072003jp",
    database="test"
)
cursor = mydb.cursor()
app = Flask(__name__)
app.secret_key="?mvf3t56@345+1234BgTNDY636773!@#"
years=['2022','2021','2020','2023']

## TESTING SESSION 

# @app.route("/test",methods=["POST","GET"])
# def test():
#     var=" "
#     if 'username' in session:
#         var=session['username']
#     return var

## ADD CLASS 

@app.route('/addclass',methods=['POST'])
def addclass():
    dept = str(request.form['dept'])
    yr = int(request.form['yr'])
    batch = str(request.form['batch'])
    subcode = str(request.form['subjcode'])#subcode
    teacher_id = str(request.form['teacherId'])#teacherid
    year = year[yr+1]
    classname=dept+'_'+year+'_'+batch#class
    tablename=classname+'_'+subcode+'att'
    student=request.form['select_students']
    namelist=json.loads(student)
    strength=len(namelist)
    rollnum=[]
    query=f"select * from class_map where teacher_id='{teacher_id}' and class='{classname}' and subcode='{subcode}'"
    cursor.execute(query)
    check = cursor.fetchone
    cursor.reset()
    Query = ""
    if not check:
        insertQuery = f"insert into class_map values({teacher_id},{classname},{subcode},{strength})"
        cursor.execute(insertQuery)
        cursor.reset()
        Query = f"create table {tablename}(c_date date "
        for i in range(len(namelist)):
            Query+="%s varchar(1),"
            rollnum.append(namelist[i])
        Query=Query[:-1]
        Query+=")" 
    else:
        Query = f"Alter table {tablename} "
        for i in range(len(namelist)):
            Query+="Add column %s varchar(1),"
            rollnum.append(namelist[i])
        Query = Query[:-1]
    cursor.execute(Query,rollnum)
    cursor.reset()
    return "Sucessfully created / updated"

## DELETE CLASS

@app.route('/deleteclass',methods=['POST'])
def deleteclass():
    classname = str(request.form['tablename'])
    subjcode = str(request.form['subjcode'])
    # teacherId = str(request.form['teacherId'])
    Query = f"drop table {classname}_{subjcode}"
    cursor.execute(Query)
    cursor.reset()
    Query = f"Delete from classdetails where subjcode = '{subjcode}'"
    cursor.execute(Query)
    cursor.reset()
    return "Successfully  deleted"

## VIEW CLASS

@app.route('/viewclass',methods = ['POST'])
def viewclass():
    teacherId = str(request.form['teacher_id'])
    Query = f"select * from class_map where teacher_id='{teacherId}'"
    cursor.execute(Query)
    detail = cursor.fetchall()
    cursor.reset()
    res={}
    c=1
    for i in detail:
        res["class"+str(c)]={"class":i[1],"course":i[2],"strength":i[3]} 
        c=c+1
    return res

## LOGIN

@app.route('/login',methods=['POST'])
def login():
    user_name = str(request.form["uname"])
    pass_word = str(request.form["pass"])   #getting name and pass
    Query = "select * from sample where name = %s and pass = %s"
    val =[user_name,pass_word]
    cursor.execute(Query,val)
    account = cursor.fetchone()
    cursor.reset()
    if account:
        # session['username']=user_name
        # print(session)
        return "true"
    else :
        return "false"    

## GETTING STUDENT DETAILS

@app.route("/studentdetails",methods=["POST"]) 
def stduentdetails():
    dept = str(request.form['dept'])
    yr = int(request.form['yr'])
    batch = str(request.form['batch'])
    year = years[yr-1]
    class_name=dept+'_'+year+'_'+'batch'+str(batch)#class
    print(class_name)
    #class_name = str(request.form["classname"])
    query = f"select * from {class_name}"
    cursor.execute(query)       
    detail = cursor.fetchall()
    cursor.reset()
    details ={detail[i][0]:detail[i][1] for i in range(0,len(detail))}
    returner = json.dumps(details)
    return returner

## IMAGE UPLOAD


@app.route("/imageUpload",methods=["Post"])
def imageUpload():

    imgdata=base64.b64decode(request.form['image'])
    img = Image.open(io.BytesIO(imgdata))
    img.save(f"finalML/frames/{uuid.uuid1()}.jpeg",quality=100)
    cropper()
    return "Image Uploaded Sucessfully"
    
## UPDATE ATTENDENCE

@app.route("/updateattendance",methods=["POST"])
def updateattendance():
    up_atten = str(request.form["att"])
    atten = json.loads(up_atten)
    tab=str(request.form["tablename"])+'_att'
    query =f"select * from {tab} where c_date = curdate()"
    cursor.execute(query)
    check = cursor.fetchone()
    cursor.reset()
    if not check:
        insert =f"insert into {tab} values(curdate(),"
        val=[]
        for i in atten:
            insert+="%s," 
            val.append(atten[i])
        insert=insert[:-1]
        insert+=")"
        cursor.execute(insert,val)
        mydb.commit()
        cursor.reset()
        return "Attendence updated sucessfully"
    else:
        return "Attendance already taken"


if __name__ == "__main__":
    app.run(host="0.0.0.0")