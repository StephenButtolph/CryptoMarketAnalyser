'''
Created on Aug 13, 2017

@author: Stephen
'''


from datetime import datetime
import json
from Logs import LogManager
from Constants import Constants

'''
Lambda entry point function
'''
def entry(event, context):
    logger = LogManager.getLogger(event)
    
    currentTime = datetime.now().strftime(Constants.LOG_TIME_FORMAT)
    logger.info('Lambda Executed at: {}'.format(currentTime))
    
    eventText = json.dumps(event)
    logger.debug('Received Event: {}'.format(eventText))
    
    

if __name__ == '__main__':
    entry(None, None)