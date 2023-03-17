import sys
import time

print(time.strftime('%Y-%m-%d %H:%M:%S', time.localtime()), ':', sys.argv[-1])