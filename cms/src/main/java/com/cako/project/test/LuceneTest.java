package com.cako.project.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneTest {

	/**
	 * 建立索引
	 */
	public static void index() {
		IndexWriter iwriter = null;
		try {
			Analyzer analyzer = new StandardAnalyzer();
			Directory directory = FSDirectory.open(new File("C:\\test\\index001").toPath());
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			iwriter = new IndexWriter(directory, config);
			Document doc = null;
			File f = new File("c:\\test\\lucene");
			for (File file : f.listFiles()) {
				doc = new Document();
				doc.add(new TextField("content",new FileReader(file)));
				doc.add(new StringField("filename",file.getName(),TextField.Store.YES));
				doc.add(new TextField("path", file.getAbsolutePath(),TextField.Store.YES));
				iwriter.addDocument(doc);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if (iwriter != null) {
					iwriter.close();
				}
				iwriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void searcher() {
		DirectoryReader reader = null;
		try {
			Directory directory = FSDirectory.open(new File("C:\\test\\index001").toPath());
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser("content",new StandardAnalyzer());
			Query query = parser.parse("java");
			TopDocs docs = searcher.search(query, 10);
			ScoreDoc[] score = docs.scoreDocs;
			for (ScoreDoc scoreDoc : score) {
				Document document = searcher.doc(scoreDoc.doc);
				System.out.println("文件名称：" + document.get("filename"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (reader!= null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		searcher();
//		index();
		String str = "Home page of The Apache Software Foundation... http://apache.dataguru.cn/lucene/java/ Backup SitesPlease use the backup mirrors only to download PGP and";

	}
}
