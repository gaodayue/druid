# Community Extensions

Please contribute all community extensions in this directory and include a README.md of how your extension can be used.

Please note that community extensions are maintained by their original contributors and are not packaged with the core Druid distribution.

## Including Community Extensions

Community extensions be download locally via the [pull-deps](http://druid.io/docs/latest/operations/pull-deps.html) tool. 
Make sure to download the dependencies from a tag or branch that matches the version of Druid you have deployed.

Once all the dependencies are downloaded, make sure to include the extension name in `druid.extensions.loadList`.

For more information, please see [here](http://druid.io/docs/latest/operations/including-extensions.html).

## Promoting Community Extension to Core Extension

Please [let us know](https://groups.google.com/forum/#!forum/druid-development) if you'd like an extension to be promoted to core. 
If we see a community extension actively supported by the community, we will promote it to core based on community feedback. 
