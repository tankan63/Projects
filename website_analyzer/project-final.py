
''' python project to build an SEO tool to input data in an excel sheet and 
find related tags in the HTML content of a website, thus printing the 
tags onto the python shell '''

from pyexcel_xls import read_data							
from bs4 import BeautifulSoup									
from urllib.request import urlopen								
import xlsxwriter

name=input("Enter the excelsheet name(with .xlsx ext.): ")

exBook=xlsxwriter.Workbook(name)			#create an excel workbook and sheet for website name
exSheet=exBook.add_worksheet()						#add the data according to the project
        

data=read_data(name)	
try:							
    for sheetname, values in data.items():
        urls=values[0]
        tags=values[1:6]
        url=urls[0]
        print(url)
        print(tags)
        print('-'*20)
        hyperLink=urlopen(url)
        html=hyperLink.read().decode('utf-8')
	
        Soup=BeautifulSoup(html, 'html.parser')			#bs4 to display the html content	
        meta=Soup.find_all('meta')
        desc=Soup.find(attrs={'name': 'description'})		
        try:															
            data=desc['content']								
            keywords=[x.strip() for x in data.split(',')]		#first splits data string as keywords. removing whitespaces to make a list of words
            for wr in keywords:									#for loop, will print said keywords
                print(wr)
        except Exception as e:										
            print('Hmmm...something is not right here.')		
		
except IndexError as i:
    print('That is the end of the list!')