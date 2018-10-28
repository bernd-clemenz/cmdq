#
# throwing an error
# (c) ISC Clemenz & Weinbrecht GmbH
#

from de.isc.cmdq.error import ScriptError

log.info("Hello world from Jython")

# this always thrown ...
if some_thing is None:
    raise ScriptError("error in script")
