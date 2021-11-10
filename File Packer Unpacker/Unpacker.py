#################################################################################################
#
# Application : File Unpacker.
# Description : It is used to Unpack files from given file into separate files.
# Functions   : 1.Entry Function, 2.FileUnpacker and 3.UnpackFile.
# Input       : File name .
# Output      : Unpack files into separate files.
# Date        : 19/06/2021.
# Author      : Harish Vijay Bavne.
#
#################################################################################################

# Required Python packeges
import os
import pathlib

##################################################################################################

# Function to get the File name and its size to unpack.
def FileUnpacker(File):
    """
    Function to get the File name and its size to unpack.
    Parameter : File path to unpack.
    
    """
    
    fd = open(File,'rb')                        # Open input file in Read binary mode.
    Readoffset = 0                              # Readoffset counter.
    FileSize = os.path.getsize(File)            # Get File Size.
    Count = 0                                   # Counter to count number of files unpacked.
    while(FileSize > Readoffset):
        Count += 1                              
        Header = fd.read(100)                   # Read Header to get packed file name and its size.
        Header = Header.decode('utf8').strip()  # Convert Header into string and remove spaces(trailing and leading).
        
        Name,Size = Header.split(" ")           # Get file name and its size to create unpacked file.
        
        UnpackFile(Name,Size,fd)                # Call to Unpack function.
        
        Readoffset = fd.tell()                  # Update Readoffset Counter.
    
    fd.close()
    print(f"{Count} Files are unpacked.")    

##################################################################################################

# Function to create unpack file.
def UnpackFile(Name,Size,fd):
    """
    Function is used to create New file to retrive data from packed file.
    Parameter : File name = to create unpacked file, Size = to read number of bytes and packed file name.
    
    """
    
    OutFile = open(Name,'wb')                   # Create file in write binary mode.
    Data = fd.read(int(Size))                   # Get the data only specific bytes.
    OutFile.write(Data)                         # Write the data in same file.
    OutFile.close()                             
    
####################################################################################################################
  
# Entry function.
def main():
    """
    Entry Function.
    Input : File name from user to unpack file and retrive original files. .
    
    """
    File = input("Enter file name:")
    FileUnpacker(File)

####################################################################################################################

# Main starter.
if __name__ =="__main__":

    print("Function main: ",main.__doc__)
    print("Function FileUnpacker: ",FileUnpacker.__doc__)
    print("Function UnpackFile: ",UnpackFile.__doc__)
    
    main()

####################################################################################################################
