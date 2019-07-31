#remember to install the gecko driver and place it in the python folder of the OS
from selenium import webdriver
from selenium.webdriver.common.keys import keys
import time

class Twitter:
    def __init__(self, username, password):
        self.username=username
        self.password=password
        self.bot=webdriver.Firefox()
        
    def login(self):
        bot=self.bot
        bot.get('https://twitter.com')
        time.sleep(4)
        user=bot.find_element_by_class_name('email-input')
        password=bot.find_element_by_name('session[password]')
        user.clear()
        password.clear()
        user.send_keys(self.username)
        password.send_keys(self.password)
        password.send_keys(keys.RETURN)
        time.sleep(4)
        
    def tweet_like(self, hashtag):
        bot=self.bot
        bot.get('https://twitter.com/search?q=' + hashtag +'&src=typd')
        time.sleep(3)
        for i in range(1,4):
            bot.execute_script('window.scrollTo(0,document.body.scrollHeight)')
            time.sleep(3)
            tweet = bot.find_elements_by_class_name('tweet')
            links = [elem.get_attribute('data-permalink-path') for elem in tweet]
            for link in links:
                bot.get('https://twitter.com'+ link)
                try:
                    bot.find_element_by_class_name('HeartAnimation').click()
                    time.sleep(30)
                except Exception as e:
                    time.sleep(90)
        
tan = Twitter('Tankan_63', 'Quaipail8')
tan.login()    
tan.tweet_like('championsleague')
    