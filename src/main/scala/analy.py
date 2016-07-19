import urllib
from optparse import OptionParser

parser = OptionParser()

parser.add_option("-u", "--url",help="url of the json result")


(options, args) = parser.parse_args()

u = options.url

ulib=urllib.urlopen(u)

print(ulib.read())

res=ulib.read()