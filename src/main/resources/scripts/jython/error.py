#
# throwing an error
# (c) ISC Clemenz & Weinbrecht GmbH
#

from java.lang import IllegalStateException

log.info("Hello world from Jython")

# this always thrown ...
if some_thing is None:
    raise IllegalStateException("error in script")
