from flask import *
import json
from select import select
import mysql.connector
import werkzeug
mydb = mysql.connector.connect(
    host="localhost",
    user="root",
    password="14072003jp",
    database="test"
)
cursor = mydb.cursor()
app = Flask(__name__)
app.secret_key="?mvf3t56@345+1234BgTNDY636773!@#"

# @app.route("/",methods=["POST"])
# def home():
#     name = request.form["uname"]
#     print(name)
#     return "hello"
@app.route('/login',methods=['POST'])
def login():
    user_name = str(request.form["uname"])
    pass_word = str(request.form["pass"])   #getting name and pass
    select = "select * from sample where name = %s and pass = %s"
    val =[user_name,pass_word]
    cursor.execute(select,val)
    account = cursor.fetchone()
    cursor.reset()
    if account:
        session['username']=user_name
        print(session)
        return "true"
    else :
        return "false"    
  
@app.route("/test",methods=["POST","GET"])
def test():
    var=" "
    if 'username' in session:
        var=session['username']
    return var

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
    app.run(host="0.0.0.0")