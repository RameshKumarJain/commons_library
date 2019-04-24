package com.ideacrest.app.mongo;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;

public class MongoService<T> {

	private ObjectMapper objectMapper;

	private Class<T> classzz;

	private MongoCollection<Document> collection;

	@Inject
	public MongoService(MongoDatabase db, ObjectMapper objectMapper, String collectionName, Class<T> classzz) {
		this.objectMapper = objectMapper;
		this.classzz = classzz;
		collection = db.getCollection(collectionName);
	}

	public void insertOne(T document) {
		Document d = convertToDocument(document);
		collection.insertOne(d);
	}

	public void save(T document, Object id) {
		Document d = convertToDocument(document);
		try {
			collection.insertOne(d);
		} catch (Exception e) {
			collection.findOneAndReplace(eq("_id", id), d, new FindOneAndReplaceOptions().upsert(true));
		}
	}

	public void updateOne(T document, Object id) {
		Document d = convertToDocument(document);
		collection.findOneAndReplace(eq("_id", id), d, new FindOneAndReplaceOptions().upsert(true));
	}

	public void insertMany(List<T> documents) {
		collection.insertMany(convertListOfDocument(documents));
	}

	public List<T> find() {
		return convertListOfBean(collection.find().into(new ArrayList<>()));
	}

	public List<T> findByKey(Bson query) {
		return convertListOfBean(collection.find(query).into(new ArrayList<>()));
	}

	public T findOneByKey(Bson query) {
		return convertBean(collection.find(query).first());
	}

	public void updateOne(BasicDBObject findQuery, BasicDBObject updateQuery) {
		collection.updateOne(findQuery, updateQuery);
	}

	public void deleteOne(Bson query) {
		collection.deleteOne(query);
	}

	private T convertBean(Object data) {
		return objectMapper.convertValue(data, classzz);

	}

	private List<T> convertListOfBean(Object data) {
		return objectMapper.convertValue(data, new TypeReference<List<T>>() {
		});
	}

	private Document convertToDocument(Object data) {
		return objectMapper.convertValue(data, Document.class);
	}

	private List<Document> convertListOfDocument(Object data) {
		return objectMapper.convertValue(data, new TypeReference<List<Document>>() {
		});
	}

}
