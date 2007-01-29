package com.randomhumans.svnindex.parsing;

import java.util.List;

import org.apache.lucene.document.Field;

public interface IFieldParser<T>
{
    List<Field> parse(T input);

}
