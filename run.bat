set classpath=lib/opennlp-tools-1.5.2-incubating.jar;lib/opennlp-maxent-3.0.3.jar;lib/lingpipe-4.1.0.jar;lib/jcommon-1.0.16.jar;lib/jfreechart-1.0.13-experimental.jar;lib/jfreechart-1.0.13-swt.jar;lib/jfreechart-1.0.13.jar;lib/stanford-corenlp-3.6.0.jar;lib/com.google.guava_1.6.0.jar;lib/org-apache-commons-logging.jar;lib/stanford-corenlp-3.6.0-models.jar;lib/slf4j-api.jar;lib/ejml-0.23.jar;lib/miglayout-3.6-swing.jar;lib/liquidlnf.jar;.;
javac -d . *.java
java -Xmx1500M com.GUI
pause