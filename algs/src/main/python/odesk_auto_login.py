#!/usr/bin/python2
# Performs login onto odesk.com
# author Serj Sintsov <ssivikt@gmail.com>, 2013 Public Domain.

import mechanize

from os.path import expanduser
import sys
import time
import string

LOGIN_URL = 'https://www.odesk.com/login'

LOGIN = sys.argv[0]
PASSWORD = sys.argv[1]

LOG_FILE = expanduser("~") + '/.odesk_auto_loggin.log'

CHECK_TOKEN = '<a href="/profile" class="oNavLink">Profile</a>'

def log(f, mes=''):
    f.write(mes)
    f.write('\n')

def main():
    log_file = open(LOG_FILE, "w+")
    
    date_time = time.asctime( time.localtime(time.time()) )
    
    log(log_file, '[%s] is start time' % date_time)
    log(log_file, 'Getting start page...')
    
    br = mechanize.Browser()
    br.open(LOGIN_URL)

    def form_checker(form):
        return 'id' in form.attrs and form.attrs['id'] == 'login'
    
    log(log_file, 'Searching form...')
    br.select_form(predicate=form_checker)
   
    log(log_file, 'Filling form...')
    br['username'] = LOGIN
    br['password'] = PASSWORD
    
    log(log_file, 'Submitting form...')
    response = br.submit()
    
    log(log_file, 'Checking have we logged in...')
    page_content = response.read()
    
    if string.find(page_content, CHECK_TOKEN) == -1:
        log(log_file, 'Bad news! We haven`t logged in!')
    else:
        log(log_file, 'We have logged in!')
    
    log(log_file)
    log_file.close()

if __name__ == '__main__':
    main()
