# from unicodedata import name
# import pandas as pd
from flask import *
import json
from select import select
import mysql.connector
import werkzeug

mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="admin",
    database="test"
)
cursor = mydb.cursor()
app = Flask(__name__)
app.secret_key="?mvf3t56@345+1234BgTNDY636773!@#"

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
    tablename = str(request.form['classname'])
    subjcode = str(request.form['subjcode'])
    teacherId = str(request.form['teacherId'])
    tablename += subjcode
    selectedstudent = str(request.form["att"])
    namelist = json.loads(selectedstudent)
    query = f"select * from {tablename}"
    cursor.execute(query)
    check = cursor.fetchone()
    cursor.reset()
    rollnum =[]
    Query = ""
    if not check:
        insertQuery = f"insert into classdetails values({teacherId},{tablename},{subjcode})"
        cursor.execute(insertQuery)
        cursor.reset()
        Query = f"create table {tablename}(c_date varchar(10)"
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
    Query = f"Delete from classdetails where subjcode = {subjcode}"
    cursor.execute(Query)
    cursor.reset()
    return "Successfully  deleted"

## VIEW CLASS

@app.route('/viewclass',methos = ['POST'])
def viewclass():
    teacherId = str(request.form['teacherid'])
    Query = f"select * from classdetails where teacherId = {teacherId}"
    cursor.execute(Query)
    detail = cursor.fetchone()
    cursor.reset()
    print(detail)

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
    class_name = str(request.form["classname"])
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
    imagefile = request.files['t1']
    if imagefile:
        filename = werkzeug.utils.secure_filename(imagefile.filename)
        print("\nReceived image File name : " + imagefile.filename)
        imagefile.save("images/"+filename)
        return "Image Uploaded sucessfully"
    else :
        return ""

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
    # print(check)
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
    app.run(host="0.0.0.0",debug=True)