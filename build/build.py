import os, re
import shutil

ROOTPATH = re.sub(r'/build', '', os.path.dirname(os.path.realpath(__file__)))
ROOTPROJ = ROOTPATH.split('/').pop()

print ROOTPROJ
print ROOTPATH

def get_target_dirs():
	target_dirs = []
	for target in os.listdir(ROOTPATH+'/..'):
		if target.startswith(ROOTPROJ) and target.endswith('_exe'):
			target_dirs.append(os.path.relpath(ROOTPATH+'/../'+target))
	return target_dirs

def distribute_log4j():
	target_dirs = get_target_dirs()
	for target in target_dirs:
		print 'Copying log4j.properties to ' + target
		shutil.copyfile(ROOTPATH+'/log4j.properties', target+'/log4j.properties')


if (__name__ == "__main__"):
	distribute_log4j()