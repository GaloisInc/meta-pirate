LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=6f3663ccdae1496a974276e71e1ff927"

SRC_URI = "git://github.com/hcrest/libfreespace.git;protocol=https \
           file://0001-switch-to-python3.patch"

PV = "1.0+git${SRCPV}"
SRCREV = "18aa2117a24b23df73f762dd3432a60b1daa3519"

S = "${WORKDIR}/git"

DEPENDS += "libusb"

inherit cmake

