import requests
from bs4 import BeautifulSoup
import smtplib
import time

url = 'https://www.amazon.in/Sony-MDR-XB450-EXTRA-Headphones-Black/dp/B00NFJGUPW/ref=sr_1_4?crid=F3VGKR5IL5YX&keywords=sony+headphone&qid=1563284203&s=gateway&sprefix=sony+%2Caps%2C858&sr=8-4'
headers = {"User-Agent":'Mozilla/5.0 (Windows NT 6.3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36'}

def checkPrice():
    page = requests.get(url, headers=headers)
    soup=BeautifulSoup(page.content, 'html.parser')
    #print(soup.prettify())
    title = soup.find(id="productTitle").get_text()
    print(title.strip())
    price=soup.find(id="priceblock_ourprice").get_text()
    Price = float(price[0:5])
    if(Price < 1400):
        sendMail()

def sendMail():
    server = smtplib.SMTP('smtp.gmail.com', 587)
    server.ehlo()
    server.starttls()
    server.ehlo()

    server.login('mehra.tanay@gmail.com', 'ffljsmkhsbhtzooq')

    sub="Price has fallen!"
    body="Check da ting nau: https://www.amazon.in/Sony-MDR-XB450-EXTRA-Headphones-Black/dp/B00NFJGUPW/ref=sr_1_4?crid=F3VGKR5IL5YX&keywords=sony+headphone&qid=1563284203&s=gateway&sprefix=sony+%2Caps%2C858&sr=8-4"
    mail=f"Subject: {sub}\n\n\n{body}"

    server.sendmail('mehra.tanay@gmail.com', 'mehra.tanay@gmx.com', mail)

    print(Mail Sent.)

    server.quit()
