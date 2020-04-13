该程序是一个java小程序， 功能是递归比对并抽取出两个目录下不同的文件，将文件即路径原样输出到一个指定的目录下。

Cu'baseDirectory' = {file| file∈fullFileDirectory, file∉baseDirectory}
即现在有基准目录baseDirectory和全集文件目录fullFileDirectory， 程序将会比对两个目录下同路径且同名的文件，将在fullFileDirectory目录下但不在baseDirecrtory目录下的文件输出到指定输出目录下。
输出的路径为截取掉fullFileDirectory全集文件目录之后的文件路径。

编译好的jar被放置在bin目录下，直接下载执行以下命令即可

使用编译好的包运行的前提条件：
  1. 需要安装jdk1.8， 并且配置了JAVA_HOME, 在cmd端执行java -version能正确显示版本信息

运行命令如下：
  java -jar ${PATH}/difference-file-extraction-${version}.jar ${baseDirectory} ${fullFileDirectory} ${outputFileDirectory}

运行参数介绍：
    ${PATH}: 放置difference-file-extraction-${version}.jar文件的路径
    ${version}: 编译后的版本，最新版本是1.0
    ${baseDirectory}: 子集文件所在的目录,需要绝对路径，windows类似'C:\Users\User\Desktop\repo_default',  Linux类似'/home/userhome/repo_default'
    ${fullFileDirectory}： 全集文件目录， 需要绝对路径
    ${outputFileDirectory}： 计算出的补集结果的输出路径。



版本信息：
    v1.0 增加抽取功能
