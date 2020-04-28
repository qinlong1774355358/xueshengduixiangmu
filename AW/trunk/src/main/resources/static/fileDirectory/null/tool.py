import glob
import re
import os
import pandas as pd 
import matplotlib.pyplot as plt
from sklearn.decomposition import PCA
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.cluster import KMeans
from sklearn.cluster import MiniBatchKMeans
from sklearn.cluster import Birch
from sklearn.metrics import accuracy_score as score
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier as KNN
import xgboost as xgb
import sys

def handle_csv(csv,leaf_dic):
	df = pd.read_csv(csv)
	df = df[df['cve_id']!='cve_id']
	df = df.dropna(subset = ['vuln_cvssv2_vector','vuln_software_cpe'])
	df = df.reset_index(drop = True)
	lenth = len(df)

	df['cvssv2_base_score'] = df['cvssv2_base_score'].str.strip()
	df_vec = df['vuln_cvssv2_vector'].str.strip()
	reg_vec = re.compile(r'\(.*\)')
	for i in range(lenth):
		df_vec[i] = re.match(reg_vec,df_vec[i]).group()
	df['vuln_cvssv2_vector'] = df_vec

	df_imp = df['vuln_cvssv2_additional'].str.strip()
	imp_1 = re.compile(r'Provides user account access') 
	imp_2 = re.compile(r'Allows disruption of service')
	imp_3 = re.compile(r'Allows unauthorized modification')
	imp_4 = re.compile(r'Provides administrator access')
	imp_5 = re.compile(r'Provides unauthorized access')
	imp_6 = re.compile(r'Allows unauthorized disclosure of information')
	imp_7 = re.compile(r'Victim must voluntarily interact with attack mechanism')
	df_imp_1 = pd.Series([0]*lenth)
	df_imp_2 = pd.Series([0]*lenth)
	df_imp_3 = pd.Series([0]*lenth)
	df_imp_4 = pd.Series([0]*lenth)
	df_imp_5 = pd.Series([0]*lenth)
	df_imp_6 = pd.Series([0]*lenth)
	df_imp_7 = pd.Series([0]*lenth)
	for i in range(lenth):
		tmp = df_imp[i]
		if re.search(imp_1,tmp) != None: df_imp_1[i] = 1
		if re.search(imp_2,tmp) != None: df_imp_2[i] = 1
		if re.search(imp_3,tmp) != None: df_imp_3[i] = 1
		if re.search(imp_4,tmp) != None: df_imp_4[i] = 1
		if re.search(imp_5,tmp) != None: df_imp_5[i] = 1
		if re.search(imp_6,tmp) != None: df_imp_6[i] = 1
		if re.search(imp_7,tmp) != None: df_imp_7[i] = 1
	df['imp_1'] = df_imp_1
	df['imp_2'] = df_imp_2
	df['imp_3'] = df_imp_3
	df['imp_4'] = df_imp_4
	df['imp_5'] = df_imp_5
	df['imp_6'] = df_imp_6
	df['imp_7'] = df_imp_7

	reg_cpe = re.compile(r'cpe:2\.3:(\w:[^:]*:[^:]*)')
	for i in range(lenth):
		tmp = re.findall(reg_cpe,df.loc[i,'vuln_software_cpe'])
		df.loc[i,'vuln_software_cpe'] = ';'.join(set(tmp))

	des = df['description']
	reg_atk_sof = re.compile(r'(.*)(?=allow)')
	reg_atk_imp = re.compile(r'(((allow.*?|makes it easier for.*?)(?=via))|((allow.*|makes it easier for.*)(?=\.)))')
	reg_atk_type = re.compile(r'(((via.*?\.\w.*|via.*?|by\s\w*ing.*?)(?=, a different))|((via.*\.\w.*|via.*|by\s\w*ing.*)(?=\.)))')
	atk_sof = pd.Series(range(lenth))
	atk_imp = pd.Series(range(lenth))
	atk_type = pd.Series(range(lenth))
	for i in range(lenth):
		if re.search(reg_atk_sof,des[i]) is None:
			atk_sof[i] = ''
		else:
			atk_sof[i] = re.search(reg_atk_sof,des[i]).group()
		if re.search(reg_atk_imp,des[i]) is None:
			atk_imp[i] = ''
		else:
			atk_imp[i] = re.search(reg_atk_imp,des[i]).group()
		if re.search(reg_atk_type,des[i]) is None:
			atk_type[i] = ''
		else:
			atk_type[i] = re.search(reg_atk_type,des[i]).group()
	df['atk_sof'] = atk_sof
	df['atk_imp'] = atk_imp
	df['atk_type'] = atk_type

	del df['cvssv3_base_score'],df['vuln_cvssv3_av'],df['vuln_cvssv3_vector'],df['cvssv3_exploitability_score'],df['cvssv3_impact_score']
	del df['last_modified_time'],df['vuln_cvssv2_av'],df['vuln_cvssv2_additional'],df['description']

	name = re.search('[0-9]*.csv',csv).group()
	df.to_csv('{}\\pre_process\\m_{}'.format(leaf_dic,name))

def data_process(sub_dic):
	csv_list = glob.glob('%s\\raw data\\[0-9]*.csv'%sub_dic)
	if os.path.isdir('%s\\pre_process'%sub_dic) is False:
		os.makedirs('%s\\pre_process'%sub_dic)
	for csv in csv_list:
		handle_csv(csv,sub_dic)
		print('%s finished'%csv)

def to_one(sub_dic):
	m_csv_list = glob.glob('%s\\pre_process\\m_[0-9]*.csv'%sub_dic)
	df = pd.DataFrame()
	for csv in m_csv_list:
		tmp = pd.read_csv(csv)
		del tmp['Unnamed: 0']
		df = df.append(tmp)
	df = df.reset_index()
	del df['index']
	df.to_csv('%s\\pre_process\\cve.csv'%sub_dic) 
	df = df.dropna(subset = ['atk_imp','atk_type','atk_sof'])  
	df = df.reset_index(drop = True)
	df.to_csv('%s\\pre_process\\dropna_cve.csv'%sub_dic)
	print('to one finished')

def linux_windows(my_dic):
	if os.path.isdir('%s\\count'%my_dic) is False:
		os.makedirs('%s\\count'%my_dic)
	csv_list = glob.glob('%s\\pre_process\\m_[0-9]*.csv'%my_dic)
	data = pd.DataFrame()
	i = 0
	file_list = ['linux_vulnerability_numbers','windows_vulnerability_numbers','linux_vulnerability_score','windows_vulnerability_score']
	l_count,w_count = 0,0
	linux_number,windows_number = list(),list()
	l_year_score,w_year_score = list(),list()
	linux_score,windows_score = list(),list()

	for csv in csv_list:
		i = i + 1
		df= pd.read_csv(csv)
		for j in range(len(df)):
			if(re.search(r'linux',df['vuln_software_cpe'][j]) != None):
				l_count = l_count + 1
				score = re.search(r'[0-9]*.[0-9]',df['cvssv2_base_score'][j]).group()
				score = float(score)
				score = float('%.1f'%score)
				l_year_score.append(score)
			if(re.search(r'windows',df['vuln_software_cpe'][j]) != None):
				w_count = w_count + 1
				score = re.search(r'[0-9]*.[0-9]',df['cvssv2_base_score'][j]).group()
				score = float(score)
				score = float('%.1f'%score)
				w_year_score.append(score)
		if(i == 12):
			linux_score.append((sum(l_year_score)/len(l_year_score)))
			linux_number.append(l_count)
			windows_score.append((sum(w_year_score)/len(w_year_score)))
			windows_number.append(w_count)
			l_year_score = []
			w_year_score = []
			i = 0

	data = pd.DataFrame()
	data['date'] = pd.Series(range(2002,2018))
	data['linux_vulnerability_numbers'] = pd.Series(linux_number)
	data['windows_vulnerability_numbers'] = pd.Series(windows_number)
	data['linux_vulnerability_score'] = pd.Series(linux_score)
	data['windows_vulnerability_score'] = pd.Series(windows_score)

	for obj in file_list:
		fig = plt.figure(figsize= (10,6))
		sub = fig.add_subplot(1,1,1)
		sub.plot(data['date'],data[obj],'-o')
		sub.set_ylabel('%s per year'%obj)
		sub.set_xlabel('date(year)')
		sub.set_xticks(data['date'])
		sub.set_title('%s analysis'%obj)
		plt.savefig('{}\\count\\{}'.format(my_dic,obj),dpi = 300)
		plt.close(fig)
		print('%s finished'%obj)

def associate(my_dic):
	def sof_handle(tp,df,my_path):
		data = pd.DataFrame()
		sof_df = pd.DataFrame()
		sof = []
		data = df[df['vuln_type'] == tp]
		for ttp in data['vuln_software_cpe']:
			p_sof = re.findall(r'\w:[^:]*:([^;]*)',ttp)
			for tt in p_sof:
				sof.append(tt)

		sof_set = set(sof)
		sof_set = list(sof_set)
		sof_df['software'] = pd.Series(sof_set)
		sof_df['frequency'] = pd.Series()
		for t_sof in sof_set:
			ct = sof.count(t_sof)
			fq = float(ct/len(sof))
			sof_df.loc[(sof_df.software == t_sof),['frequency']] = fq
		sof_df = sof_df.sort_values(by='frequency',ascending = False)
		sof_df = sof_df.reset_index()
		del sof_df['index']
		sof_df = sof_df.ix[:29]

		sof_df['frequency'] = sof_df['frequency']*1000
		fig = plt.figure(figsize = (12,8))
		sub = fig.add_subplot(1,1,1)
		sub.bar(range(30),sof_df['frequency'])
		sub.set_ylabel('frequency(‰)')
		sub.set_xlabel('software')
		title = re.search(r'CWE-others|CWE-[0-9]*',tp).group()
		sub.set_title('%s_to_software.ass_analysis'%title)
		sub.set_xticks(range(30))
		sub.set_xticklabels(sof_df['software'],rotation = 85)
		plt.subplots_adjust(left=0.13,bottom=0.38, top=0.9)
		plt.savefig('{}\\associate\\sof_{}'.format(my_path,title),dpi = 300)
		print('%s finished'%title)
		plt.close(fig)

	def imp_handle(tp,df,my_path):
		data = pd.DataFrame()
		imp_df = pd.DataFrame()
		imp_df['imp_type'] = pd.Series(['imp_1','imp_2','imp_3','imp_4','imp_5','imp_6','imp_7'])
		data = df[df['vuln_type'] == tp]
		frequency=[]
		for i in range(7):
			i = i+1
			imp_fq = float(sum(data['imp_%d'%i])/len(data))
			frequency.append(imp_fq)
		imp_df['frequency'] = pd.Series(frequency)
		imp_df = imp_df.reset_index()
		del imp_df['index']

		imp_df['frequency'] = imp_df['frequency']*100
		fig = plt.figure(figsize = (8,6))
		sub = fig.add_subplot(1,1,1)
		sub.bar(imp_df['imp_type'],imp_df['frequency'])
		sub.set_ylabel('frequency(%)')
		sub.set_xlabel('imp_type')
		title = re.search(r'CWE-others|CWE-[0-9]*',tp).group()
		sub.set_title('%s_to_ImpactType.ass_analysis'%title)
		plt.subplots_adjust(left=0.13,bottom=0.12, top=0.9)
		plt.savefig('{}\\associate\\imp_{}'.format(my_path,title),dpi = 300)
		print('%s finished'%title)
		plt.close(fig)

	def language(my_path):
		label = ['C++:CWE-119/189/264/399','C:CWE-119/189/264/399','Java:CWE-264/399',
			'C#:CWE-264','Interpreted language:CWE-94','Assembly language:CWE-119']
		sizes = [30,30,16,8,8,8]
		color = ['r','g','b','c','y','m']
		fig = plt.subplot()
		fig.set_title('language-relevant vulnerability distribution')
		fig.pie(sizes,labels=label,colors=color)
		plt.subplots_adjust(left=0.25,right = 0.7,top = 0.75)
		plt.savefig('%s\\associate\\language analysis'%my_path,dpi = 300)
		print('associate finished')
		plt.close()

	if os.path.isdir('%s\\associate'%my_dic) is False:
		os.makedirs('%s\\associate'%my_dic)
	p_df = pd.read_csv('%s\\pre_process\\cve.csv'%my_dic)
	t_list = set(p_df['vuln_type'])
	df = pd.DataFrame()
	main_type = ['Cross-Site Scripting (XSS) (CWE-79)','Numeric Errors (CWE-189)','Buffer Errors (CWE-119)','Code Injection (CWE-94)',
	'Input Validation (CWE-20)','Permissions, Privileges, and Access Control (CWE-264)','Cryptographic Issues (CWE-310)',
	'SQL Injection (CWE-89)','Information Leak / Disclosure (CWE-200)','Path Traversal (CWE-22)','Improper Access Control (CWE-284)',
	'Resource Management Errors (CWE-399)', 'Authentication Issues (CWE-287)','Cross-Site Request Forgery (CSRF) (CWE-352)']
	for tp in t_list:
		flt = p_df[p_df['vuln_type'] == tp]
		if((tp!='Insufficient Information (NVD-CWE-noinfo)')&(tp!='Other (NVD-CWE-Other)')):
			if tp in main_type:
				df = df.append(flt)
			else:
				flt.loc[:,['vuln_type']] = '(CWE-others)'
				df = df.append(flt)
		else:
			flt.loc[:,['vuln_type']] = '(CWE-others)'
			df = df.append(flt)
	df = df.reset_index()
	del df['index']
	tp_list = set(df['vuln_type'])
	for tp in tp_list:
		sof_handle(tp,df,my_dic)
		imp_handle(tp,df,my_dic)
	language(my_dic)

def cluster(my_dic):	
	def handle(method,data_set):
		with open('stop_word.txt') as f:
			f_stop = f.read()
			f_stop = f_stop.split('\n')
		vectorizer = CountVectorizer(stop_words = f_stop, token_pattern = r'\b[a-zA-Z]+\b', max_features = 300)
		X = vectorizer.fit_transform(data_set).toarray()
		de_X = PCA(n_components = 2).fit_transform(X)
		if data_set == imp_data:
			obj = 'vulnerability impact'
		if data_set == type_data:
			obj = 'attack type'
		if data_set == sof_data:
			obj = 'affected software'
		clusters = range(3,9)
		for n in clusters:
			if method == 'KMeans':
				clu = KMeans(n_clusters = n)
			if method == 'MiniBatchKMeans':
				clu = MiniBatchKMeans(n_clusters = n)
			if method == 'Birch':
				clu = Birch(n_clusters = n)
			colors = clu.fit_predict(de_X)
			fig = plt.figure()
			sub = fig.add_subplot(1,1,1)
			sub.scatter(de_X[:,0],de_X[:,1],c = colors)
			sub.set_title('clustering {} with {}(k={})'.format(obj,method,n))
			plt.savefig('{}\\cluster\\{}-{}(k={})'.format(my_dic,obj,method,n),dpi = 300)
			print('{}\\cluster\\{}-{}(k={}) finished'.format(my_dic,obj,method,n))
			plt.close(fig)

	if os.path.isdir('%s\\cluster'%my_dic) is False:
		os.makedirs('%s\\cluster'%my_dic)
	df = pd.read_csv('%s\\pre_process\\dropna_cve.csv'%my_dic)
	imp_data = list(df['atk_imp'])
	type_data = list(df['atk_type'])
	sof_data = list(df['atk_sof'])
	method_list = ['MiniBatchKMeans','KMeans','Birch']
	for mth in method_list:
		handle(mth,imp_data)
		handle(mth,type_data)
		handle(mth,sof_data)

def classification(my_dic):
	def preprocess():
		csv_list = glob.glob('%s\\raw data\\[0-9]*.csv'%my_dic)
		raw = pd.DataFrame()
		data = pd.DataFrame()
		description = []
		vul = []  
		for csv in csv_list:
			df = pd.read_csv(csv)
			for i in range(len(df)):
				description.append(df['description'][i])
				vul.append(df['vuln_type'][i])
		
		raw['type'] = pd.Series(vul)
		raw['description'] = pd.Series(description)
		tp_set = set(vul)
		main_type = ['Cross-Site Scripting (XSS) (CWE-79)','Numeric Errors (CWE-189)','Buffer Errors (CWE-119)','Code Injection (CWE-94)',
		'Input Validation (CWE-20)','Permissions, Privileges, and Access Control (CWE-264)','Cryptographic Issues (CWE-310)',
		'SQL Injection (CWE-89)','Information Leak / Disclosure (CWE-200)','Path Traversal (CWE-22)','Improper Access Control (CWE-284)',
		'Resource Management Errors (CWE-399)', 'Authentication Issues (CWE-287)','Cross-Site Request Forgery (CSRF) (CWE-352)']
		for tp in tp_set:
			flt = raw[raw['type'] == tp]
			if((tp!='Insufficient Information (NVD-CWE-noinfo)')&(tp!='Other (NVD-CWE-Other)')):
				if tp in main_type:
					data = data.append(flt)   
		data = data.reset_index()
		del data['index']
		data.to_csv('%s\\pre_process\\pre-classify.csv'%my_dic)

	def classify(): 
		data = pd.read_csv('%s\\pre_process\\pre-classify.csv'%my_dic)
		x,y = list(data['description']),list(data['type'])
		for i in range(len(y)):
			if(y[i] == 'Cross-Site Scripting (XSS) (CWE-79)'):
				y[i] = 1
			if(y[i] == 'Numeric Errors (CWE-189)'):
				y[i] = 2
			if(y[i] == 'Buffer Errors (CWE-119)'):
				y[i] = 3
			if(y[i] == 'Code Injection (CWE-94)'):
				y[i] = 4
			if(y[i] == 'Input Validation (CWE-20)'):
				y[i] = 5
			if(y[i] == 'Permissions, Privileges, and Access Control (CWE-264)'):
				y[i] = 6
			if(y[i] == 'Cryptographic Issues (CWE-310)'):
				y[i] = 7
			if(y[i] == 'SQL Injection (CWE-89)'):
				y[i] = 8
			if(y[i] == 'Information Leak / Disclosure (CWE-200)'):
				y[i] = 9
			if(y[i] == 'Path Traversal (CWE-22)'):
				y[i] = 10
			if(y[i] == 'Improper Access Control (CWE-284)'):
				y[i] = 11
			if(y[i] == 'Resource Management Errors (CWE-399)'):
				y[i] = 12
			if(y[i] == 'Authentication Issues (CWE-287)'):
				y[i] = 13
			if(y[i] == 'Cross-Site Request Forgery (CSRF) (CWE-352)'):
				y[i] = 0
		with open('stop_word.txt') as f:
			f_stop = f.read()
			f_stop = f_stop.split('\n')
		vectorizer = CountVectorizer(stop_words = f_stop, token_pattern = r'\b[a-zA-Z]+\b',max_features= 280)
		X = vectorizer.fit_transform(x).toarray()
		de_X = PCA(n_components=14).fit_transform(X)
		X_train,X_test,y_train,y_test = train_test_split(X,y,test_size = 0.2,random_state = 1)
		de_xtrain,de_xtest,de_ytrain,de_ytest = train_test_split(de_X,y,test_size = 0.2,random_state = 1)

		def knn_alg(xtrain,ytrain,xtest,ytest):
			n_neigh = range(1,10)
			train_score,test_score = list(),list()
			for nei in n_neigh:
				alg = KNN(n_neighbors=nei)
				alg.fit(xtrain,ytrain)
				train_pre = alg.predict(xtrain)
				test_pre = alg.predict(xtest)
				train_aft = score(ytrain,train_pre)
				test_aft = score(ytest,test_pre)
				train_score.append(train_aft)
				test_score.append(test_aft)
			plt.title('machine learning result with KNN')
			plt.xlabel('n_neighbors')
			plt.ylabel('learning accuracy(%)')
			plt.ylim(0.5,1)
			plt.plot(n_neigh,train_score,'r-x',label = 'training set')
			plt.plot(n_neigh,test_score,'b-x',label = 'testing set')
			plt.legend()
			plt.savefig('%s\\classify\\KNN'%my_dic,dpi = 300)
			print('KNN traing finished')
			plt.close()

		def tree_alg(xtrain,ytrain,xtest,ytest):
			n_dep = range(5,35,3)
			train_score,test_score = list(),list()
			for depth in n_dep:
				alg = DecisionTreeClassifier(max_depth=depth)
				alg.fit(xtrain,ytrain)
				train_pre = alg.predict(xtrain)
				test_pre = alg.predict(xtest)
				train_aft = score(ytrain,train_pre)
				test_aft = score(ytest,test_pre)
				train_score.append(train_aft)
				test_score.append(test_aft)
			plt.title('machine learning result with DecisionTree')
			plt.xlabel('max_depth')
			plt.ylabel('learning accuracy(%)')
			plt.ylim(0.5,1)
			plt.plot(n_dep,train_score,'r-x',label = 'training set')
			plt.plot(n_dep,test_score,'b-x',label = 'testing set')
			plt.legend()
			plt.savefig('%s\\classify\\DesicionTree'%my_dic,dpi = 300)
			print('tree traing finished')
			plt.close()
		
		def xgb_alg(xtrain,ytrain,xtest,ytest):
			dtrain = xgb.DMatrix(xtrain,label=ytrain)
			dtest = xgb.DMatrix(xtest)
			params = {
				'booster': 'gbtree',
				'objective': 'multi:softmax',
				'num_class': 14,
				'max_depth': 6,
				'eta': 0.2,
				'lambda':0.005,
				'alpha':0,
				'gamma':0,
			}
			n_tree = range(10,100,10)
			train_score,test_score = list(),list()
			for num_round in n_tree:
				alg = xgb.train(params,dtrain,num_round)       
				train_pre = alg.predict(dtrain)
				test_pre = alg.predict(dtest)
				train_aft = score(ytrain,train_pre)
				test_aft = score(ytest,test_pre)
				train_score.append(train_aft)
				test_score.append(test_aft)
			plt.title('machine learning result with xgboost')
			plt.xlabel('n_estimators(n_trees)')
			plt.ylabel('learning accuracy(%)')
			plt.ylim(0.5,1)
			plt.plot(n_tree,train_score,'r-x',label = 'training set')
			plt.plot(n_tree,test_score,'b-x',label = 'testing set')
			plt.legend()
			plt.savefig('%s\\classify\\xgboost'%my_dic,dpi = 300)
			print('xgboost traing finished')
			plt.close()

		xgb_alg(X_train,y_train,X_test,y_test)
		knn_alg(de_xtrain,de_ytrain,de_xtest,de_ytest)
		tree_alg(de_xtrain,de_ytrain,de_xtest,de_ytest)

	if os.path.isdir('%s\\classify'%my_dic) is False:
		os.makedirs('%s\\classify'%my_dic)		
	preprocess()
	classify()

def usage():
	doc = """
	[Error]
		Usage:	tool.py [associate|cluster|classify|count|all]

		for example:
			python tool.py associate
			python tool.py cluster
	"""
	print(doc)

if '__main__' == __name__:
	work_dic = os.getcwd()
	if(len(sys.argv[0:]) != 2):
		usage()
	else:
		func = sys.argv[1]
		if func in ['associate','cluster','classify','count','all']:
			if os.path.exists('%s\\pre_process\\cve.csv'%work_dic) == False:
				data_process(work_dic)
				to_one(work_dic)
			if func == 'associate':
				associate(work_dic)
			if func == 'cluster':
				cluster(work_dic)
			if func == 'classify':
				classification(work_dic)
			if func == 'count':
				linux_windows(work_dic)
			if func == 'all':
				linux_windows(work_dic)
				associate(work_dic)
				cluster(work_dic)
				classification(work_dic)
		else:
			usage()
	



	
	


ain,de_xtest,de_ytest)

	if os.path.isdir('%s\\classify'%my_dic) is False:
		os.makedirs('%s\\classify'%my_dic)		
	preprocess()
	classify()

def usage():
	doc = """
	[Error]
		Usage:	tool.py [associate|cluster|classify|count|all]

		for example:
			python tool.py associate
			python tool.py cluster
	"""
	print(doc)

if '__main__' == __name__:
	work_dic = os.getcwd()
	if(len(sys.argv[0:]) != 2):
		usage()
	else:
		func = sys.argv[1]
		if func in 