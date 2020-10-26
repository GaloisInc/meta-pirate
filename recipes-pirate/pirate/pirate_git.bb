LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://doc/LICENSE;md5=eff14c7a617d5e3f95a7b263c81c95e2"

SRC_URI = "git://github.com/GaloisInc/pirate.git;protocol=http \
           file://0001.dev.patch"

PV = "1.0+git${SRCPV}"

# Fetches the latest master, use commit hash to fetch a specific version
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git"

REQUIRED_DISTRO_FEATURES = "x11"

FILES_${PN} += "${libdir}/* ${includedir} /opt/pirate/* /opt/pirate/demos*"

inherit cmake

EXTRA_OECMAKE = "-DGAPS_DISABLE=ON -DPIRATE_UNIT_TEST=ON -DPIRATE_LAUNCHER=OFF -DPIRATE_GETOPT=OFF -DGAPS_DEMOS=ON"
DEPENDS = "googletest jpeg libx11 openssl"
RDEPENDS_${PN} += "bash"

do_install() {
    # Headers
    install -d ${D}/${includedir}
    install -m 0644 ${S}/libpirate/libpirate.h ${D}/${includedir}
    install -m 0644 ${S}/libpirate/libpirate.hpp ${D}/${includedir}

    # Library
    install -d ${D}/${libdir}
    install -m 0755 ${B}/libpirate/libpirate.so ${D}/${libdir}/libpirate.so.1.0

    # Demos
    install -d ${D}/opt/pirate/demos

    # Camera demo
    install -d ${D}/opt/pirate/demos/camera_demo
    install -m 0755 ${B}/demos/camera_demo/camera_demo_gaps ${D}/opt/pirate/demos/camera_demo
    install -m 0755 ${B}/demos/camera_demo/camera_demo_monolith ${D}/opt/pirate/demos/camera_demo

    # Time demo
    install -d ${D}/opt/pirate/demos/time_demo
    install -d ${D}/opt/pirate/demos/time_demo/orange
    install -m 0755 ${B}/demos/time_demo/orange/sensor_manager ${D}/opt/pirate/demos/time_demo/orange
    install -m 0755 ${B}/demos/time_demo/orange/ts_verify.sh ${D}/opt/pirate/demos/time_demo/orange
    install -d ${D}/opt/pirate/demos/time_demo/orange/stock
    install -m 0644 ${B}/demos/time_demo/orange/stock/*.jpg ${D}/opt/pirate/demos/time_demo/orange/stock
    install -d ${D}/opt/pirate/demos/time_demo/purple
    install -m 0755 ${B}/demos/time_demo/purple/signing_service ${D}/opt/pirate/demos/time_demo/purple
    install -d ${D}/opt/pirate/demos/time_demo/purple/.priv
    install -m 0644 ${B}/demos/time_demo/purple/.priv/tsa* ${D}/opt/pirate/demos/time_demo/purple/.priv
    install -d ${D}/opt/pirate/demos/time_demo/yellow
    install -m 0755 ${B}/demos/time_demo/yellow/signing_proxy ${D}/opt/pirate/demos/time_demo/yellow
    install -d ${D}/opt/pirate/demos/time_demo/ca/.priv
    install -m 0644 ${B}/demos/time_demo/ca/.priv/tsa_ca_key.pem ${D}/opt/pirate/demos/time_demo/ca/.priv
    install -d ${D}/opt/pirate/demos/time_demo/tsa
    install -m 0644 ${B}/demos/time_demo/tsa/tsa* ${D}/opt/pirate/demos/time_demo/tsa
    install -d ${D}/opt/pirate/demos/time_demo/test
    install -m 0755 ${B}/demos/time_demo/test/* ${D}/opt/pirate/demos/time_demo/test

    # Unit test
    install -d ${D}/opt/pirate/utest
    install -m 0755 ${B}/libpirate/gaps_channels_test ${D}/opt/pirate/utest

    # Library symbolic links
    cd ${D}/${libdir}
    ln -sf libpirate.so.1.0 libpirate.so
}
