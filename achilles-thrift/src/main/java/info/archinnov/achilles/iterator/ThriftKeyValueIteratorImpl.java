package info.archinnov.achilles.iterator;

import info.archinnov.achilles.context.ThriftPersistenceContext;
import info.archinnov.achilles.entity.metadata.PropertyMeta;
import info.archinnov.achilles.iterator.factory.ThriftKeyValueFactory;
import info.archinnov.achilles.type.KeyValue;
import info.archinnov.achilles.type.KeyValueIterator;

import java.util.NoSuchElementException;

import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.beans.HColumn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThriftKeyValueIteratorImpl
 * 
 * @author DuyHai DOAN
 * 
 */
public class ThriftKeyValueIteratorImpl<K, V> implements KeyValueIterator<K, V>
{
	private static final Logger log = LoggerFactory.getLogger(ThriftKeyValueIteratorImpl.class);

	private ThriftKeyValueFactory factory = new ThriftKeyValueFactory();
	protected ThriftAbstractSliceIterator<HColumn<Composite, V>> sliceIterator;
	private PropertyMeta<K, V> propertyMeta;
	private ThriftPersistenceContext context;

	protected ThriftKeyValueIteratorImpl() {}

	public ThriftKeyValueIteratorImpl(ThriftPersistenceContext context,
			ThriftAbstractSliceIterator<HColumn<Composite, V>> columnSliceIterator,
			PropertyMeta<K, V> propertyMeta)
	{
		this.context = context;
		this.sliceIterator = columnSliceIterator;
		this.propertyMeta = propertyMeta;
	}

	@Override
	public boolean hasNext()
	{
		log.trace("Does the {} has next value ? ", sliceIterator.type());
		return this.sliceIterator.hasNext();
	}

	@Override
	public KeyValue<K, V> next()
	{
		log.trace("Get next key/value from the {} ", sliceIterator.type());
		KeyValue<K, V> keyValue = null;
		if (this.sliceIterator.hasNext())
		{
			HColumn<Composite, V> column = this.sliceIterator.next();
			keyValue = factory.createKeyValue(context, propertyMeta, column);
		}
		else
		{
			throw new NoSuchElementException();
		}
		return keyValue;
	}

	@Override
	public K nextKey()
	{
		log.trace("Get next key from the {} ", sliceIterator.type());
		K key = null;
		if (this.sliceIterator.hasNext())
		{
			HColumn<Composite, ?> column = this.sliceIterator.next();
			key = factory.createKey(propertyMeta, column);
		}
		else
		{
			throw new NoSuchElementException();
		}
		return key;
	}

	@Override
	public V nextValue()
	{
		log.trace("Get next value from the {} ", sliceIterator.type());
		V value = null;
		if (this.sliceIterator.hasNext())
		{
			HColumn<Composite, V> column = this.sliceIterator.next();
			value = factory.createValue(context, propertyMeta, column);
		}
		else
		{
			throw new NoSuchElementException();
		}
		return value;
	}

	@Override
	public Integer nextTtl()
	{
		log.trace("Get next ttl from the {} ", sliceIterator.type());
		Integer ttl = null;
		if (this.sliceIterator.hasNext())
		{
			HColumn<Composite, ?> column = this.sliceIterator.next();
			ttl = factory.createTtl(column);
		}
		else
		{
			throw new NoSuchElementException();
		}
		return ttl;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException(
				"Remove from iterator is not supported. Please use removeValue() or removeValues() instead");
	}

}
