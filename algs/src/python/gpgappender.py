#!/usr/bin/python
# -*- coding: utf-8 -*-
# author Serj Sintsov <ssivikt@gmail.com>, 2010, Public Domain.


"""This script adds key-value pair line to the file encrypted by GPG
"""
import os
import sys
import shutil
import uuid
import subprocess
import getpass

SOURCE_PWDS_FILE_PATH = sys.argv[0]
SOURCE_PWDS_FILE_COPY_PATH = sys.argv[1]
RES_AND_PASS_SEPARATOR = " - "   

"""
"""
def print_process_out(p):
    for line in p.stdout.readlines():
        print(line)

"""
"""
def append_new_resource(file_path, resource_name, password, separator):
    with open(file_path, "a") as f:
        f.write(resource_name + separator + password + "\n")

"""
"""
def decrypt_file(file_path):
    print("Descrypt '" + file_path + "'")
    
    decrypted_file_path = str(uuid.uuid4())

    decrypt_cmd = "gpg -o " + decrypted_file_path + " -d " + file_path
    gpg_decrypt_process = subprocess.Popen(decrypt_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    
    print_process_out(gpg_decrypt_process)

    decrypt_retval = gpg_decrypt_process.wait()

    if decrypt_retval == 1:
        print("GPG exited with error")
        sys.exit(1)
    else:
        return decrypted_file_path
            
"""
"""
def encrypt_file(source_file_path, dest_file_path):
    print("Encrypt '" + source_file_path + "' to '" + dest_file_path + "'")

    encrypt_cmd = "gpg -o " + dest_file_path + " -c " + source_file_path
    gpg_encrypt_process = subprocess.Popen(encrypt_cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    
    print_process_out(gpg_encrypt_process)

    encrypt_retval = gpg_encrypt_process.wait()

    if encrypt_retval == 1:
        print("GPG exited with error")
        sys.exit(1)

"""
"""
def make_a_copy(source_file_path, cp_to):
    print("Make a copy of '" + source_file_path + "' to '" + cp_to + "'")
    shutil.copyfile(source_file_path, cp_to)

"""
"""
def rm_file(file_path):
    os.remove(file_path)

"""
"""
def read_password():
    pprompt = lambda: (getpass.getpass(), getpass.getpass("Retype password: "))

    password1, password2 = pprompt()
    while password1 != password2:
        print("Passwords do not match. Try again")
        password1, password2 = pprompt()
    
    return password1

def main(args):
    if len(args) < 2:
        print("Usage: gpgappender.py resource_name\n")
        sys.exit(1)

    resource_name = args[1]
    password = read_password()     
        
    decrypted_file_path = decrypt_file(SOURCE_PWDS_FILE_PATH)
    
    append_new_resource(decrypted_file_path, resource_name, password, RES_AND_PASS_SEPARATOR)
    encrypt_file(decrypted_file_path, SOURCE_PWDS_FILE_PATH)
    make_a_copy(SOURCE_PWDS_FILE_PATH, SOURCE_PWDS_FILE_COPY_PATH)
    rm_file(decrypted_file_path)
    
if __name__ == '__main__':
    main(sys.argv)
    
