import unittest
from Platforms.Poloniex import Poloniex
 
class TestPoloniex(unittest.TestCase):
    def setUp(self):
        self.poloniex = Poloniex(None, None)
        
    '''
    Public
    '''
 
    def test_returnTicker(self):
        ticker = self.poloniex.returnTicker()
        
        self.assertNotIn('error', ticker)
        
    def test_return24hVolume(self):
        volume = self.poloniex.return24hVolume()
        
        self.assertNotIn('error', volume)
        
    def test_returnOrderBook(self):
        orderBook = self.poloniex.returnOrderBook('BTC_ETH')
        
        self.assertNotIn('error', orderBook)
        
    def test_returnMarketTradeHistory(self):
        marketTradeHistory = self.poloniex.returnMarketTradeHistory('BTC_ETH')
        
        self.assertNotIn('error', marketTradeHistory)
        
    '''
    
    '''
 
if __name__ == '__main__':
    unittest.main()