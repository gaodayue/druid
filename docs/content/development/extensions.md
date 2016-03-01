---
layout: doc_page
---

# Core extensions

Druid comes with a number of core extensions to enhance functionality. These extensions can be enabled in the `druid.extensions.loadList` config. 
For more information, please see [here](../operations/including-extensions.html).

Core extensions include support for:

* Apache Avro
* Apache Kafka 0.8.x (high level consumer)
* Apache Kafka 0.8.x (low level consumer)
* Approximate Histograms & Quantiles
* HDFS
* Kafka namespace lookup
* MySQL metadata store
* Namespaced lookups
* PostgreSQL metadata store
* S3-compatible deep storage
* Yahoo! Datasketches

Core extensions are maintained by Druid committers.

# Community Extensions

A number of community members have contributed their own extensions to Druid that are not packaged with the default Druid tarball.
  
These include support for:

* Apache Cassandra
* Apache Kafka 0.7.x
* Graphite
* Microsoft Azure
* RabbitMQ
* Rackspace Cloudfiles
* RocketMQ
  
For more about community extensions, please see [here](https://github.com/druid-io/druid/tree/master/extensions-contrib/README.md). 
Community extensions are maintained by the community.
