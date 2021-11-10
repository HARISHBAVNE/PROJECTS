#################################################################################################
#
# Application : File Packer.
# Description : It is used to Pack files into single file.
# Functions   : 1.Entry Function, 2.FilePacker, 3.Pack, 4.Checksum and 5.CreateFile.
# Input       : Directory ,file name .
# Output      : Packed data of many files into one file.
# Date        : 18/06/2021.
# Author      : Harish Vijay Bavne.
#
#################################################################################################

# Required Python packeges
import os
import pathlib
import hashlib

##################################################################################################

# Function to Travel directory
def FilePacker(Directory,Name):
    """
    Function is used to travel Directory to get the file names.
    Parameter : Directory to travel,String for output file name.
    
    """
    Extension = [".txt",".c",".cpp",".java",".py"]
    
    path = os.getcwd()
    FilePath = os.path.join(path,Name)
    fd = CreateFile(FilePath)                               #Call function to create output file.
    fdl = open("Log.txt",'a')                               # Create Log file on current directory.
    Count = 0
    for Folder,Subfolder,FileName in os.walk(Directory):
        
        for file in FileName:
           ext = pathlib.Path(file).suffix
           
           if (ext in Extension):                           #Filter for specific extension files.          
               Count = Count + 1
               name = os.path.join(Directory,file)          #File name with path.
               size = os.path.getsize(name)                 #Size of file.
               hash = Checksum(name)                        #Call Checksum function to gethte Checksum of file.
               
               fdl.write(f"File Name:{file}\nSize of file:{size}\nCheck Sum of file:{hash}\n\n") # Log file details.
               
               Pack(name,size,FilePath)                     #Call to pack function for packing file into one file.
    print(f"{Count} files are packed.")
    fd.close()
    fdl.close()
####################################################################################################################
  
# Function to Pack files in one File.
def Pack(File,Size,Outfile):                                 
    """
    Function is used to Pack file into output file with its name and data.
    Parameter : 1.File which get packed,2.Size of that file and 3.Combine file
    in which files get packed.
    
    """
    fdout = open(Outfile,'a')                               #Open output file in append mode. 
    fdin = open(File,'r')                                   #Input file open in read mode.
    FileName = os.path.basename(File)
    print(FileName)
    
    Header = (FileName + " " + str(Size))                   #Header string created.
    Data = fdin.read()                                      #Data of input file.
    n = len(Header)
    
    for i in range(n+1,101):
        Header = Header + " "                               #Update Header.
    
    fdout.write(Header)                                     #Write Header in output file.
    fdout.write(Data)                                       #write Data into Output file.
    fdout.close()
    fdin.close()
    
####################################################################################################################    

# Function for Checksum.
def Checksum(name,blocksize = 1024):
    """
    Function is used to get the Checksum of file.
    Parameter : File name and default parameter blocksize = 1024
    Return    : Return check sum.
    
    """


    hobj = hashlib.md5()

    fd = open(name,'rb')
    buffer = fd.read(blocksize)
    while(len(buffer) > 0):
        hobj.update(buffer)
        buffer = fd.read(blocksize)
    return (hobj.hexdigest())

####################################################################################################################
# Function to Create a file       
def CreateFile(path):
    """
    Function is used to create output file.
    Parameter : Path to create output file.
    Return    : Output file.
    
    """
    return open(path,'a')
####################################################################################################################


# Entry function.
def main():
    """
    Entry Function.
    Input : Takes Directory name and string for output file name from user.
    
    """
    
    Directory = input("Enter a folder name:")
    Name = input("Provide file name for packing:")

    FilePacker(Directory,Name)                              #Call to Function FilePacker.

####################################################################################################################

# Main starter.
if __name__ =="__main__":

    print("Functions details")
    print("Function FilePacker:",FilePacker.__doc__)
    print("Function CreateFile:",CreateFile.__doc__)
    print("Function Checksum:",Checksum.__doc__)
    print("Function Pack:",Pack.__doc__)
    
    main()

####################################################################################################################
