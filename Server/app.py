from flask import Flask, render_template, request, redirect, url_for, session
from flask_mysqldb import MySQL
import MySQLdb.cursors
import re
import hashlib
from datetime import datetime
import json

from matplotlib.font_manager import json_dump

app = Flask(__name__)


app.secret_key = 'Tahve bqltuyej tbrjereq qobfd MvIaTq cmanmvpcuxsz iesh tihkel CnTu dretpyauritompeanstd '


#app.config['MYSQL_HOST'] = 'localhost'
app.config['MYSQL_USER'] = 'admin@localhost'
app.config['MYSQL_PASSWORD'] = 'MITapp123!@#'
app.config['MYSQL_DB'] = 'mit_users'
app.config['MYSQL_PORT'] = 3306


mysql = MySQL(app)

print(mysql)

@app.route('/login', methods=['POST'])
def login():
    email = request.json.get('email')
    password = request.json.get('password')
    hash_object = hashlib.sha256(password.encode('ascii'))
    hash_password = hash_object.hexdigest()
    returner = {}
    cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
    cursor.execute('SELECT * FROM accounts WHERE email = %s AND hash = %s', (email, hash_password,))
    account = cursor.fetchone()

    if account:
        session['loggedin'] = True
        session['id'] = account['id']
        session['email'] = account['email']
        session['utype']=account['utype']
        returner['status']="login success"
        returner['utype']=session['utype']
    else:
        returner['status']="login failure"
        returner['utype']="None"
    return returner
        
@app.route('/logout', methods=['POST'])
def logout():
    
   returner = {}
   session.pop('loggedin', None)
   session.pop('email', None)
   session.pop('id', None)
   session.pop('username', None)
   session.pop('utype', None)
   returner['status']="logout success"
   return returner
   

@app.route('/register', methods=['POST'])
def register():
    returner = {}
    username = request.json.get('username')
    print(username)
    password = request.json.get('password')
    print (password)
    email = request.json.get('email')
    print (email)
    utype = request.json.get('utype')
    print (utype)
    cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
    cursor.execute('SELECT * FROM accounts WHERE email = %s', (email,))
    account = cursor.fetchone()
    hash_object = hashlib.sha256(password.encode('ascii'))
    hash_password = hash_object.hexdigest()   
    if account:
        returner['status']= 'Account already exists'
    elif not re.match(r'[^@]+@[^@]+\.[^@]+', email):
        returner['status']=  'Invalid email address'
    elif not re.match(r'[A-Za-z0-9]+', username):
        returner['status']=  'Username must contain only characters and numbers!'
    elif not username or not password or not email:
        returner['status']=  'Please fill out the form'
    else:
        cursor.execute('INSERT INTO accounts VALUES (NULL, %s, %s, %s, %s)', (username, hash_password, email,utype,))
        mysql.connection.commit()
        returner['status']=  'You have successfully registered!'
    return returner

@app.route('/registercomplaint', methods=['POST'])
def regcomplaint():
    returner = {}
    data1 = {}
    if ( session['loggedin'] == False ):
         returner['status']= 'Only Logged in candidates can issue a Complaint.'
    elif ( session['utype'] == 'electrician' or session['utype'] == 'civil and maintenance' or session['utype'] == 'education aid' ):
        returner['status']= 'Only Student and Teachers can issue a Complaint.'
    else:
         Block = request.json.get('Block')
         Floor = request.json.get('Floor')
         RoomNo = request.json.get('RoomNo')
         Complaint = request.json.get('Complaint')
         cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
         if (Block == "None"):
          cursor.execute('SELECT Block FROM roomdata group by Block',) 
          data1 = cursor.fetchall()
          data1 = list(data1)
          returner['data'] = data1
          return returner
         elif (Floor == "None"):
             cursor.execute('SELECT Floor FROM roomdata WHERE Block = %s group by floor' , (Block,))
             data1 = cursor.fetchall()
             data1 = list(data1)
             returner['data'] = data1
             return returner
         elif (RoomNo == "None"):
             cursor.execute('SELECT RoomNo FROM roomdata WHERE Block = %s and Floor = %s', (Block,Floor,))
             data1 = cursor.fetchall()
             data1 = list(data1)
             returner['data'] = data1
             return returner
         elif (Complaint == "None"):
             cursor.execute('SELECT complaints FROM complaintslist')
             data1 = cursor.fetchall()
             data1 = list(data1)
             returner['data'] = data1
             return returner
         cursor.execute('SELECT status FROM complaints where block = %s and roomno = %s and floor = %s and complaint = %s', (Block, RoomNo, Floor, Complaint,))
         sts = cursor.fetchall()
         if sts:
            sts = json.dumps(sts)
            if 'Registered' in sts:
                returner['status'] = 'Complaint already exists!'
                return returner
         cursor.execute('SELECT complainttype FROM complaintslist where complaints = %s', (Complaint,))
         complainttype = cursor.fetchone()
         complainttype = json.dumps(complainttype)
         complainttype = complainttype.replace('{','')
         complainttype = complainttype.replace('}','')
         complainttype = complainttype.replace('"','')
         complainttype = complainttype.replace(':','')
         complainttype = complainttype.replace('complainttype','')
         complainttype = complainttype.replace(' ','')
         timenow = datetime.now()
         cts = timenow.strftime("%d/%m/%y %H:%M:%S")
         cursor.execute('INSERT INTO complaints VALUES (NULL, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)', (session['email'], Block, int(Floor), RoomNo, Complaint, complainttype, "Registered", cts, cts, session['utype'],))
         mysql.connection.commit()
         returner['status']=  'You have successfully registered a Complaint.'
         return returner

# /view_complaint
@app.route('/viewcomplaint', methods=['POST'])
def viewcomplaint():
    returner = {}
    if ( session['loggedin'] == False ):
         returner['status']= 'Only Logged in candidates can view a Complaint.'
    else:
        if (session['utype'] == 'admin'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, block, floor, roomno, complaint, complainttype, status, utype from complaints")
            cmpl = cursor.fetchall()
            cmpl = list(cmpl)
            returner['complaint'] = cmpl
            return returner
        elif (session['utype'] == 'student' or session['utype'] == 'teacher'):
            if (session['utype'] == 'student'):
                cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
                cursor.execute("select complaintid, block, floor, roomno, complaint, complainttype, status from complaints where email=%s", (session['email'],))
                cmpl = cursor.fetchall()
                cmpl = list(cmpl)
                returner['complaint'] = cmpl
                return returner
            elif (session['utype'] == 'teacher'):
                cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
                cursor.execute("select complaintid, block, floor, roomno, complaint, complainttype, status from complaints where email=%s", (session['email'],))
                cmpl = cursor.fetchall()
                cmpl = list(cmpl)
                returner['complaint'] = cmpl
                return returner
        elif (session['utype'] == 'electrician'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complaints.complainttype = %s ",(session['utype'],))
            cmpl = cursor.fetchall()
            cmpl = list(cmpl)
            returner['complaint'] = cmpl
            return returner
        elif (session['utype'] == 'civil and maintenance'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complaints.complainttype = %s ",(session['utype'],))
            cmpl = cursor.fetchall()
            cmpl = list(cmpl)
            returner['complaint'] = cmpl
            return returner
        elif (session['utype'] == 'education aid'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complaints.complainttype = %s ",(session['utype'],))
            cmpl = cursor.fetchall()
            cmpl = list(cmpl)
            returner['complaint'] = cmpl
            return returner
        else:
               cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
               cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and users.utype= %s; ",(session['utype'],))
               cmpl = cursor.fetchall()
               cmpl = list(cmpl)
               returner['complaint'] = cmpl
               return returner

# /change_complaint_status [viewed/resolved/verified]
@app.route('/change_complaint_status', methods=['POST'])
def change_complaint_status():
    returner = {}
    if ( session['loggedin'] == False ):
         returner['status']= 'Only Logged in candidates can change complaint status.'
    else:
        num = request.json.get('complaintid')
        Status = request.json.get('Status')
        print(session['utype'])
        if (session['utype']=='student' or session['utype']=='teacher') : 
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select * from complaints where complaintid=%s and email=%s",(int(num),session['email'],))
            data1 = cursor.fetchone()
            if data1:
                timenow = datetime.now()
                uts = timenow.strftime("%d/%m/%y %H:%M:%S")
                cursor.execute("update complaints set status=%s, uts=%s WHERE complaintid=%s ",(Status, uts, num,))
                mysql.connection.commit()
                returner['status']=  'You have successfully changed the status.'
                return returner
            else:
                returner['status']=  'The complaintid doesnot exist.'
                return returner
        elif (session['utype']=='electrician'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complainttype= %s and complaintid=%s ",(session['utype'], num,))
            data = cursor.fetchone()
            if data:
                    timenow = datetime.now()
                    uts = timenow.strftime("%d/%m/%y %H:%M:%S")
                    cursor.execute("update complaints set status=%s, uts=%s WHERE complaintid=%s ",(Status, uts, num,))
                    mysql.connection.commit()
                    returner['status']=  'You have successfully changed the status.'
                    return returner
            else:
                returner['status']=  'The complaintid doesnot exist.'
                return returner
        elif (session['utype']=='education aid'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complainttype= %s and complaintid=%s ",(session['utype'], num,))
            data = cursor.fetchone()
            if data:
                timenow = datetime.now()
                uts = timenow.strftime("%d/%m/%y %H:%M:%S")
                cursor.execute("update complaints set status=%s, uts=%s WHERE complaintid=%s ",(Status, uts, num,))
                mysql.connection.commit()
                returner['status']=  'You have successfully changed the status.'
                return returner
            else:
                returner['status']=  'The complaintid doesnot exist.'
                return returner
        elif (session['utype']=='civil and maintenance'):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select complaintid, complaints.block, floor, roomno, complaint, complainttype, status, cts, uts from complaints, users where complaints.block = users.block and complainttype= %s and complaintid=%s ",(session['utype'], num,))
            data = cursor.fetchone()
            if data:
                timenow = datetime.now()
                uts = timenow.strftime("%d/%m/%y %H:%M:%S")
                cursor.execute("update complaints set status=%s, uts=%s WHERE complaintid=%s ",(Status, uts, num,))
                mysql.connection.commit()
                returner['status']=  'You have successfully changed the status.'
                return returner
            else:
                returner['status']=  'The complaintid doesnot exist.'
                return returner
        elif(True):
            cursor = mysql.connection.cursor(MySQLdb.cursors.DictCursor)
            cursor.execute("select * from complaints, users where complaintid=%s and complaints.block = users.block and users.utype=%s ",(num,session['utype'],))
            data = cursor.fetchone()
            if data:
                if (Status =="Resolved"):
                    timenow = datetime.now()
                    uts = timenow.strftime("%d/%m/%y %H:%M:%S")
                    cursor.execute("update complaints set status=%s, uts=%s WHERE complaintid=%s ",(Status, uts, num,))
                    mysql.connection.commit()
                    returner['status']=  'You have successfully changed the status.'
                    return returner
            else:
                returner['status']=  'The complaintid doesnot exist.'
                return returner
        returner['status']=  'The complainttype doesnt exist.'
        return returner
        

app.run(host="0.0.0.0",port=(8081))

    
