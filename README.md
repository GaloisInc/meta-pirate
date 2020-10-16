# PIRATE Yocto Layer

Yocto is a flexible and complex framework for building custom Linux
distributions and images. Refer to [References](README.md#References) for
documentation and tutorials.

This repository provides a Yocto layer with PIRATE components. In particular,

Recipes for:
* PIRATE library
* PIRATE library unit test
* PIRATE Demos (TODO)

Images:
* image-pirate

# Building a Custom Image
## Dependencies
```
sudo apt-get install gawk wget git-core diffstat unzip texinfo gcc-multilib build-essential chrpath socat libsdl1.2-dev xterm
```
## Directory Structure and Repositories
```
mkdir yocto
cd yocto
mkdir build layers downloads sstate-cache

# Clone the poky repository
git clone -b dunfell git://git.yoctoproject.org/poky

# Clone Yocto layers
cd layers
git clone -b dunfell https://github.com/GaloisInc/meta-pirate.git
git clone -b dunfell git://git.yoctoproject.org/meta-raspberrypi
git clone -b dunfell https://github.com/openembedded/meta-openembedded.git
git clone -b dunfell https://github.com/kraj/meta-clang.git
cd ..
```

## Configure Build Environment
```
cd build
source ../poky/oe-init-build-env qemuarm
```
**NOTE:** *qemuarm* directory was created and the script changed directory to it.
The name *qemuarm* is arbitrary; usually selected to reflect the target machine.
For example, *rpi3_64*.

### Add Yocto Layers to the Configuration Script
Edit **conf/bblayers.conf**; add the following lines:
```
    <change to your path>/yocto/layers/meta-pirate \
    <change to your path>/yocto/layers/meta-clang \
    <change to your path>/yocto/layers/meta-raspberrypi \
    <change to your path>/yocto/layers/meta-openembedded/meta-oe \
    <change to your path>/yocto/layers/meta-openembedded/meta-python \
    <change to your path>/yocto/layers/meta-openembedded/meta-multimedia \
```

### Configure Build Parameters
Edit **vim conf/local.conf**

#### Set the Target Machine
Update the *MACHINE* variable
```
MACHINE ??= "qemuarm"
```
Alternative options:
* raspberrypi3-64
* qemux86
* qemux86-64

To get a list of supported machines use:
```
ls meta*/conf/machine/*.conf
```
In *layers* or *poky* directories

#### Update Downloads and Sstate-Cache Locations (optional)
To optimize builds for multiple machines, fetched sources and build artifacts
should be stored outside of the build directory

Un-comment and update the *DL_DIR* and *SSTATE_DIR* variables
```
DL_DIR ?= "<change to your path>/yocto/downloads"
SSTATE_DIR ?= "<change to your path>/yocto/sstate-cache"
```

#### Update Package Management (optional)
By default Yocto uses RPM packages; switch it to IPK
Update the *PACKAGE_CLASSES* variable
```
PACKAGE_CLASSES ?= "package_ipk"
```

#### Switch Toolchain to clang
Add the *TOOLCHAIN* variable
```
TOOLCHAIN = "clang"
```

#### Allow Compilation of Commercially-Licensed Sotware
Add the *LICENSE_FLAGS_WHITELIST* variable
```
LICENSE_FLAGS_WHITELIST = " commercial_libav commercial_x264 commercial"
```

#### Additional Changes for Raspberry Pi Platforms
**NOTE** Apply this change only when a Raspberry Pi is the target machine.
Add the *IMAGE_FSTYPES* variable to enable generation of SD card images.
```
IMAGE_FSTYPES = "tar.xz ext3 rpi-sdimg"
```
If the target system is not using a monitor, add the *GPU_MEM* to reduce the
amount of memory allocated to GPU.
```
GPU_MEM = "16"
```

## Build
**bitbake** is the Yocto build tool
```
bitbake image-pirate
```
The first successful execution of this command will take a long time.

## Run

### QEMU
```
runqemu qemuarm
```
**NOTE**
* sudo is needed to create a virtual network device on the host machine. Add **slirp** option if the QEMU instance does not need to communicate via network
* use **nographic** to run the instance in no graphic (terminal only) mode

### Raspberry Pi
Use **dd** to write image to a SD card:
```
cd cd tmp/deploy/images/raspberrypi*
ls image-pirate-raspberrypi*.rootfs.rpi-sdimg
```

## Pirate Components
* Include files ```/usr/include```
* PIRATE shared libraries ```/usr/include```
* PIRATE unit test /opt/pirate/utest
* PIRATE demos /opt/pirate/demos

# References
* [Yocto Project Quick Start](https://www.yoctoproject.org/docs/1.8/yocto-project-qs/yocto-project-qs.html)
* [Yocto Project Development Tasks Manual](https://www.yoctoproject.org/docs/latest/dev-manual/dev-manual.html)
* [Yocto Project Reference Manual](https://www.yoctoproject.org/docs/latest/ref-manual/ref-manual.html)
* [Live Coding with Yocto Project](https://www.youtube.com/watch?v=ThTl4FItfMI&list=PLD4M5FoHz-TxMfBFrDKfIS_GLY25Qsfyj)
