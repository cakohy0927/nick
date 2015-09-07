package com.cako.project.big;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cako.project.column.entity.News;
import com.cako.project.column.service.NewsService;
import com.orm.commons.exception.ServiceException;
import com.orm.commons.utils.HtmlSplit;

@Service
public class DataBaseIndexer {

	@Autowired
	private NewsService newsService;
	private final String indexDir = "d:\\lucene\\index";

	public void createIndex() {
		try {
			// 为表字段建立索引
			Directory dir = new SimpleFSDirectory(new File(indexDir).toPath());
			Analyzer luceneAnalyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(luceneAnalyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			IndexWriter indexWriter = new IndexWriter(dir, iwc);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			List<News> list = newsService.queryByMap(paramMap);
			for (News news : list) {
				Document doc = new Document();
				doc.add(new StringField("title", news.getTitle(), Field.Store.YES));
				doc.add(new StringField("content", new HtmlSplit(news.getContent(), -1).doStartTag(), Field.Store.YES));
				doc.add(new StringField("createTime", DateTools.dateToString(new Date(), DateTools.Resolution.DAY), Field.Store.YES));
				indexWriter.addDocument(doc);
			}
			System.out.println("numDocs ： " + indexWriter.numDocs());
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public List<String> searcher(String content) {
		List<String> idList = new ArrayList<String>();
		DirectoryReader reader = null;
		try {
			Directory directory = FSDirectory.open(new File(indexDir).toPath());
			reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			String[] fields = new String[]{"title"," content "};
			MultiFieldQueryParser  multiFieldQueryParser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
			multiFieldQueryParser.setDefaultOperator(QueryParser.OR_OPERATOR);
			Query query = multiFieldQueryParser.parse(content);
			TopDocs topDocs = searcher.search(query, 10);
			ScoreDoc[] score = topDocs.scoreDocs;
			System.out.println("topDocs共检索出 " + topDocs.totalHits + " 条记录");
			for (ScoreDoc scoreDoc : score) {
				Document document = searcher.doc(scoreDoc.doc);
				String id = document.get("id");
				idList.add(id);
				System.out.println("文件名称：" + document.get("title"));
			}
			rangeQuery(content);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return idList;
	}

	public void rangeQuery(String content) {
		try {
			FSDirectory directory = FSDirectory.open(new File(indexDir).toPath());
			DirectoryReader reader = DirectoryReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			TermRangeQuery termRangeQuery = new TermRangeQuery("content", new BytesRef(content.getBytes()),
					new BytesRef(content.getBytes()), true, true);
			TopDocs topDocs = searcher.search(termRangeQuery, 1000);
			System.out.println("共检索出 " + topDocs.totalHits + " 条记录");
			System.out.println();

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (ScoreDoc scDoc : scoreDocs) {
				Document document = searcher.doc(scDoc.doc);
				String id = document.get("id");
				String title = document.get("title");
				String createTime = document.get("createTime");
				float score = scDoc.score; // 相似度

				System.out.println(String.format("id:%s, title:%s, createTime:%s, 相关度:%s.", id, title, createTime, score));
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
