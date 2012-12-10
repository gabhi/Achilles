package fr.doan.achilles.entity.metadata.builder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.prettyprint.cassandra.serializers.SerializerTypeInferer;
import me.prettyprint.hector.api.Serializer;
import fr.doan.achilles.entity.metadata.MultiKeyWideMapMeta;

/**
 * InternalMultiKeyWideMapPropertyMetaBuilder
 * 
 * @author DuyHai DOAN
 * 
 */
public class MultiKeyWideMapMetaBuilder<K, V> extends WideMapMetaBuilder<K, V>
{

	private List<Class<?>> componentClasses;
	private List<Method> componentGetters;
	private List<Method> componentSetters;

	public MultiKeyWideMapMetaBuilder(Class<K> keyClass, Class<V> valueClass) {
		super(keyClass, valueClass);
	}

	public static <K, V> MultiKeyWideMapMetaBuilder<K, V> multiKeyWideMapPropertyMetaBuiler(
			Class<K> keyClass, Class<V> valueClass)
	{
		return new MultiKeyWideMapMetaBuilder<K, V>(keyClass, valueClass);
	}

	public MultiKeyWideMapMeta<K, V> build()
	{
		MultiKeyWideMapMeta<K, V> propertyMeta = new MultiKeyWideMapMeta<K, V>();
		super.build(propertyMeta);

		propertyMeta.setComponentGetters(componentGetters);
		propertyMeta.setComponentSetters(componentSetters);

		List<Serializer<?>> keySerializers = new ArrayList<Serializer<?>>();
		for (Class<?> keyClass : componentClasses)
		{
			keySerializers.add(SerializerTypeInferer.getSerializer(keyClass));
		}

		propertyMeta.setComponentSerializers(keySerializers);

		return propertyMeta;

	}

	public MultiKeyWideMapMetaBuilder<K, V> componentClasses(List<Class<?>> componentClasses)
	{
		this.componentClasses = componentClasses;
		return this;
	}

	public MultiKeyWideMapMetaBuilder<K, V> componentGetters(List<Method> componentGetters)
	{
		this.componentGetters = componentGetters;
		return this;
	}

	public MultiKeyWideMapMetaBuilder<K, V> componentSetters(List<Method> componentSetters)
	{
		this.componentSetters = componentSetters;
		return this;
	}
}
