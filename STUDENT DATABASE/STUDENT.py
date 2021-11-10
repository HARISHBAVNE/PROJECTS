
#============================================                
# Import
#============================================
import tkinter
from tkinter import ttk


from tabulate import tabulate
from collections import defaultdict
import time
class node:
    def __init__(self,No,Name,DOB,Gender):
        self.RollNo = No
        self.Name = Name
        self.DOB = DOB
        self.Gender = Gender
        self.AdmissionDate = None
        self.Attendance = 0
        self.Next = None
        self.prev = None
# Class 
class Student:
    Size = 0;
    def __init__(self):
        self.Head = None
        self.Tail = None

    # for New Admission
    def NewAdmission(self,No,Name,DOB,Gender):              
        newn = node(No,Name,DOB,Gender)
        if (Student.Size == 0):
            self.Head = newn
            self.Tail = newn
            self.Head.AdmissionDate = time.ctime()
            Student.Size += 1
            
        else:
            newn.Next = self.Head
            newn.Next.prev = newn
            newn.AdmissionDate = time.ctime()
            self.Head = newn
            
            Student.Size += 1

    #Update Attendance
    def UpdateAttendence(self,Name,No):                     
        temp = self.Head
        for i in range(Student.Size):
            if temp.Name == Name:
                temp.Attendance = No
                break
            temp = temp.Next
    # Number of girls in class
    def GirsInClass(self):                                  
        iCnt = 0
        temp = self.Head
        for i in range(Student.Size):
            if temp.Gender == "FEMALE":
                iCnt += 1
            temp = temp.Next
        return iCnt
    # Number of Boys in class
    def BoysInClass(self):                                  
        iCnt = 0
        temp = self.Head
        for i in range(Student.Size):
            if temp.Gender == "MALE":
                iCnt += 1
            temp = temp.Next
        return iCnt
    
    #Strenth of class
    def Count(self):                                        
        return Student.Size
    
    # Display details of class
    def Display(self):                                      
        details = defaultdict(list)
        temp = self.Head
        for i in range(Student.Size):
            
            details["RollNo"].append(temp.RollNo)
            details["Name"].append(temp.Name)
            details["Attendance(in %)"].append(temp.Attendance)
            details["Date of Birth"].append(temp.DOB)
            details["Gender"].append(temp.Gender)
            details["AdmissionDate"].append(temp.AdmissionDate)
            temp = temp.Next
        #For printing Data in tabular format     
        print(tabulate(details,headers = "keys",tablefmt="github"))

    #Student present in class or not
    def CheckStudent(self,Name):                            
        temp = self.Head
        flag = 0
        for i in range(Student.Size):
            if temp.Name == Name:
                flag = 1
                break
            temp = temp.Next
        if (flag == 1):
            print(f"Yes,{temp.Name}'s Roll No is {temp.RollNo}")
        else:
            print("Result not found")
    # Admission cancel
    def AdmissionCancel(self,Name):                      
        temp = self.Head
        temp1 = self.Tail
        if (self.Head.Name == Name):
            self.Head = self.Head.Next
            self.Head.prev = None
            del(temp)
            Student.Size -= 1
            
        if (self.Tail.Name == Name):
            self.Tail.prev.Next = None
            del(temp1)
            Student.Size -= 1
        else:
            temp = self.Head
            for i in range(Student.Size-1):
                if temp.Name == Name:
                    temp.Next.prev = temp.prev
                    temp.prev.Next = temp.Next
                    del (temp)
                    Student.Size -= 1
                    break
                temp = temp.Next
#============================================                
# Main Sarter
#============================================
def main():
   
    obj = Student()        #Object of Student class
    def menu():
        print("*********************************")
        print("1:For New Admission")
        print("2:Number of Girls in class")
        print("3:Number of Boys in class")
        print("4:Update attendance by student Name")
        print("5:Student present in class or not")
        print("6:Cancel Admission")
        print("7:Strenth of class")
        print("8:Display all details of class")
        print("0:Exit application")
        print("*********************************")

    menu()  
    choice = int(input("Enter your choice"))
        
    while choice != 0:
        if choice == 1:
            No = int(input("Enter a Roll Number:"))
            Name = input("Enter a name:")
            Name = Name.upper()
            DOB = input("Enter a date of Birth:")
            Gender = input("Enter a gender male or female:")
            Gender = Gender.upper()
            obj.NewAdmission(No,Name,DOB,Gender)
        elif choice == 2:
            print(obj.GirsInClass())
                
        elif choice == 3:
            print(obj.BoysInClass())
        
        elif choice == 7:
            print(obj.Count())
        elif choice == 8:
            obj.Display()
        elif choice == 0:
            print("Thank you for using application")
            break
        else:
            print("Please Enter a Valid Choice from Menu")
        
        menu()
        choice = int(input("Enter your choice"))
        
if __name__ == "__main__":
    main()
