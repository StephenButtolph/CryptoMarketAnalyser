'''
Created on Aug 13, 2017

@author: Stephen
'''

import Constants
from datetime import datetime
import json

'''
Lambda entry point function
'''
def entry(event, context):
    currentTime = datetime.now().strftime(Constants.LOG_TIME_FORMAT)
    print('Lambda Executed at: {}'.format(currentTime))
    
    eventText = json.dumps(event)
    print('Received Event: {}'.format(eventText))

if __name__ == '__main__':
    entry(None, None)