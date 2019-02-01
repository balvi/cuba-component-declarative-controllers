# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.8.0] - 01.02.2019

### Added
- This release supports the usage within a CUBA 7 application. However - it does not support the new CUBA 7 Screen APIs.
  This release is more for fast upgrading your existing application if you rely on this app component. CUBA 7 Screens API
  will be supported in later releases.

### Changed (Breaking!)
- `ButtonsPanelHelper.getOrCreateButton` does not use `Component.Container`, but `com.haulmont.cuba.gui.components.ComponentContainer` as required in CUBA 7


### Dependencies
- CUBA 7.0.x

## [0.7.0] - 24.10.2018

### Dependencies
- CUBA 6.10.x

## [0.6.0] - 05.06.2018

### Dependencies
- CUBA 6.9.x

## [0.5.0] - 12.02.2018

### Dependencies
- CUBA 6.8.x

## [0.4.0] - 6.11.2017

### Dependencies
- CUBA 6.7.x

