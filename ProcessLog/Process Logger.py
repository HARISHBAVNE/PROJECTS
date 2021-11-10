#####################################################################################################
#
# Application : Periodic Process Logger with Auto Scheduled Log report Facility.
# Description : It is used to provide the information about PID,Username,name, and memory usage.
# Functions   : 1.Entry Function, 2.ProcessDisplay, 3.Pack, and MailSender.
# Input       : Directory.
# Output      : Log report of Process running on system.
# Date        : 13/05/2021.
# Author      : Harish Vijay Bavne.
#
#####################################################################################################

# Required Packages import.
from sys import *
import os
import psutil
import time
import schedule
import smtplib
from email.message import EmailMessage

#####################################################################################################

# Function to get the process information. 
def ProcessDisplay(FolderName = "ProcessLog"):
    """
    Function: ProcessDisplay()
    Function is used to create directory and file for regular report.
    Input   : Directory.
    Output  : Directory and log file creation.
    Pass the log file path to mail sender function.
    
    """
    if not os.path.exists(FolderName):                      # Check Directory present or not.
        os.mkdir(FolderName)                                # if not present the created.
        
    FilePath = os.path.join(FolderName,"LogReport%s.txt"%time.ctime())  # create path to create log file.
    FilePath = FilePath.replace(":"," ")
    fd = open(FilePath,'a')                                 # Create log file.
        
    Data = []                                               # Empty list for process information.
    
    for proc in psutil.process_iter():                      # Get the process information.
        value = proc.as_dict(['pid','username','name'])
        Data.append(value)
        
    for i in Data:                                          # Write a process information in to file
        fd.write("%s\n"%i)  
    
    MemoryUsage = str(psutil.virtual_memory())              # Information about memory usage.
    fd.write(f"Information about memory usage{MemoryUsage}")
    fd.close()                                              
    
    MailSender(FilePath)                                    # call a function to send mail.

#####################################################################################################
    
# FUnction is used to send mail.    
def MailSender(FilePath): 
    """
    Function : MailSender()
    Function is used to send mail.
    Input    : Log File path.
    Output   : Send mail with log file attachment.
    
    """
    
    Mail = EmailMessage()                                   # Object of EmailMessage.
    Mail['subject'] = "Log report of Processes runing on system and its application"   # Subject.
    Mail['from'] = "HARISH PM199"   
    Mail['To'] = "Receiver mail id."                    # Receiver mail id.
    
    with open("msg.txt") as txt:                            # Draft template file.
        text = txt.read()
        Mail.set_content(text)                              # Draft.
    with open(FilePath,'rb') as LogFile:
        FileName = LogFile.name
        Data = LogFile.read()
        Mail.add_attachment(Data,maintype = "Log File",subtype = "txt",filename = FileName)  # Attach Attachment.
     
    server = smtplib.SMTP_SSL("smtp.gmail.com",465)         # SMTP sever.
    server.login("Sender mail id","Password")                      # Login credential.
    server.send_message(Mail)                               # Send email.
    server.quit()

#####################################################################################################

# Entry Function.
def main():
    print("***********Log Report of Process runing on sysytem***************")
    if (len(argv)<2):
        print("Insufficient arguments")
    print("Application file name is:",argv[0])
    
    if ((argv[1] == '-u') or (argv[1] == '-U')):
        print("Usage : Application_Name Scheule_Time Directory_Name")
    if ((argv[1] == '-h')or(argv[1] == '-H')):
        print("Help : It is used to create log file of running processess")
    
    schedule.every(int(argv[1])).minutes.do(ProcessDisplay)  # Schedular.
    while True:
        schedule.run_pending()
        time.sleep(1)
        
#####################################################################################################

# Main Starter.
if __name__ == "__main__":
    main()
    
#####################################################################################################
