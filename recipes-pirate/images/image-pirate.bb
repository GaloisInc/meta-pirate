SUMMARY = "Image with PIRATE components."

include recipes-sato/images/core-image-sato.bb

IMAGE_INSTALL += "vim mpeg2dec vlc x11vnc"
IMAGE_FEATURES += "ssh-server-dropbear"
IMAGE_FEATURE_remove += "psplash"

IMAGE_FEATURES += "dev-pkgs"

#LICENSE_FLAGS_WHITELIST = " commercial_libav commercial_x264 commercial"

CONF_VERSION = "1"
TOOLCHAIN = "clang"

