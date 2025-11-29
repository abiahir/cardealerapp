# Changelog

All notable changes to this project will be documented in this file.

## [1.0.0] - 2024-11-29

### Major Restructuring

#### Added
- **Python Package Structure**
  - Proper `src/` layout with `cardealer` package
  - Modular code separation (cli, models, presentation, constants)
  - `__main__.py` for module execution
  - `setup.py` and `pyproject.toml` for modern Python tooling
  - Test structure with example tests

- **Java Maven Project**
  - Standard Maven directory layout
  - Modern Java 17+ implementation
  - Picocli for CLI with autocomplete support
  - Proper package structure (`com.cardealer`)
  - Maven assembly plugin for executable JAR

- **Documentation**
  - Main README with comprehensive overview
  - Python-specific README
  - Java-specific README
  - Contribution guidelines
  - Project structure documentation
  - Naming conventions guide
  - Various review and verification reports

- **Configuration Files**
  - `setup.py` for Python package installation
  - `pyproject.toml` for modern Python configuration
  - `pom.xml` for Maven build
  - `.pylintrc` for code quality
  - `MANIFEST.in` for package data
  - `.gitignore` for both languages

#### Changed
- **Project Structure**
  - Moved all Python code to `python/` directory
  - Moved all Java code to `java/` directory
  - Organized sample data in `python/data/`
  - Created proper test directories

- **Code Organization**
  - Split monolithic script into modular components
  - Separated concerns (CLI, models, presentation)
  - Added type hints in Python
  - Added Javadoc in Java
  - Improved error handling

- **Naming Conventions**
  - Renamed documentation files to follow conventions
  - All files follow language-specific best practices
  - Consistent naming throughout project

#### Fixed
- Fixed `pom.xml` XML tag (`<n>` → `<name>`)
- Removed duplicate files
- Cleaned up build artifacts
- Fixed import paths
- Verified all code compiles without errors

#### Removed
- Old root-level `generate_ppt.py` script
- Duplicate configuration files
- Duplicate data files
- Build artifacts (target/, __pycache__)
- Test output files

### Technical Details

#### Python
- Package: `cardealer`
- Modules: `cli`, `models`, `presentation`, `constants`
- Entry point: `python -m cardealer`
- Dependencies: `python-pptx==0.6.21`, `pillow==10.4.0`

#### Java
- Package: `com.cardealer`
- Classes: `GeneratePpt`, `PresentationBuilder`, `VehicleListing`, `DealerDetails`
- Build: Maven with executable JAR
- Dependencies: Apache POI 5.2.5, Picocli 4.7.5, Jackson 2.16.1

### Migration Guide

#### Python
**Old:**
```bash
python generate_ppt.py --input sample_vehicle.json --output listing.pptx
```

**New:**
```bash
cd python
pip install -e .
python -m cardealer --input data/sample_vehicle.json --output listing.pptx
```

#### Java
**Old:**
```bash
# Files were in root/src/main/java/
```

**New:**
```bash
cd java
mvn clean package
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json --output listing.pptx
```

---

## Format

This changelog follows [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

