import Constants
import logging

logger = None

def getLogger(event = None):
	global logger
	
	if logger is not None:
		return logger
	
	if event is not None and Constants.LOGGING_LEVEL_KEY in event:
		logLevel = event[Constants.LOGGING_LEVEL_KEY]
	else:
		logLevel = Constants.DEFAULT_LOGGING_LEVEL
		
	logging.basicConfig(level=logLevel)
	logger = logging.getLogger()
	return logger	
		
	