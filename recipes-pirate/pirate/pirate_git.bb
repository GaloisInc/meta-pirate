LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://doc/LICENSE;md5=eff14c7a617d5e3f95a7b263c81c95e2"

SRC_URI = "git://github.com/GaloisInc/pirate.git;protocol=http"

PV = "1.0+git${SRCPV}"

# Fetches the latest master, use commit hash to fetch a specific version
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

FILES_${PN} += "${libdir}/* /opt/pirate/*"
FILES_${PN}-dev = "${libdir}/* ${includedir}"

inherit cmake

EXTRA_OECMAKE = "-DGAPS_DISABLE=ON -DPIRATE_UNIT_TEST=ON -DPIRATE_LAUNCHER=OFF -DPIRATE_GETOPT=OFF"
DEPENDS = "googletest"

do_install() {
    # Headers
    install -d ${D}/${includedir}
    install -m 0644 ${S}/libpirate/libpirate.h ${D}/${includedir}
    install -m 0644 ${S}/libpirate/libpirate.hpp ${D}/${includedir}

    # Library
    install -d ${D}/${libdir}
    install -m 0755 ${B}/libpirate/libpirate.so ${D}/${libdir}/libpirate.so.1.0
    install -m 0755 ${B}/libpirate/libpirate++.so ${D}/${libdir}/libpirate++.so.1.0

    # Unit test
    install -d ${D}/opt/pirate/utest
    install -m 0755 ${B}/libpirate/gaps_channels_test ${D}/opt/pirate/utest

    # Library symbolic links
    cd ${D}/${libdir}
    ln -sf libpirate.so.1.0 libpirate.so
    ln -sf libpirate++.so.1.0 libpirate++.so
}
