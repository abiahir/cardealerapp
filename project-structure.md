# Project Structure

This document describes the complete project structure following best development practices.

## 📁 Directory Tree

```
cardealerapp/
│
├── python/                          # Python implementation
│   ├── src/
│   │   └── cardealer/               # Main package
│   │       ├── __init__.py         # Package exports
│   │       ├── __main__.py         # Allow: python -m cardealer
│   │       ├── cli.py              # Command-line interface
│   │       ├── constants.py        # Configuration constants
│   │       ├── models.py           # Data models
│   │       └── presentation.py     # PowerPoint generation
│   │
│   ├── tests/                       # Unit tests
│   │   ├── __init__.py
│   │   └── test_models.py          # Model tests
│   │
│   ├── data/                        # Sample data files
│   │   ├── sample_vehicle.json
│   │   └── ford_kuga.json
│   │
│   ├── requirements.txt             # Python dependencies
│   ├── setup.py                     # Package setup (setuptools)
│   ├── pyproject.toml               # Modern Python config
│   ├── MANIFEST.in                  # Package data files
│   ├── .pylintrc                    # Pylint configuration
│   └── README.md                    # Python-specific docs
│
├── java/                            # Java implementation
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/cardealer/
│   │   │   │       ├── GeneratePpt.java
│   │   │   │       ├── PresentationBuilder.java
│   │   │   │       ├── VehicleListing.java
│   │   │   │       └── DealerDetails.java
│   │   │   └── resources/           # Resource files
│   │   │
│   │   └── test/
│   │       └── java/com/cardealer/  # Unit tests
│   │
│   ├── pom.xml                      # Maven configuration
│   ├── .gitignore                   # Java-specific ignores
│   └── README.md                    # Java-specific docs
│
├── docs/                            # Additional documentation
│
├── .gitignore                       # Root gitignore
├── README.md                        # Main project README
├── CONTRIBUTING.md                  # Contribution guidelines
├── LICENSE                          # MIT License
└── project-structure.md             # This file
```

## 🎯 Best Practices Implemented

### Python Structure

✅ **Package Organization**
- Proper `src/` layout (prevents import issues)
- Clear separation of concerns (models, presentation, CLI)
- `__init__.py` with proper exports
- `__main__.py` for module execution

✅ **Configuration**
- `setup.py` for traditional installation
- `pyproject.toml` for modern Python tooling
- `requirements.txt` for dependencies
- `.pylintrc` for code quality

✅ **Testing**
- Dedicated `tests/` directory
- Example test file included
- Ready for pytest

✅ **Data Management**
- Sample data in `data/` directory
- Separate from source code

### Java Structure

✅ **Maven Standard Layout**
- `src/main/java` for source code
- `src/test/java` for tests
- `src/main/resources` for resources
- Proper package structure (`com.cardealer`)

✅ **Build Configuration**
- `pom.xml` with proper dependencies
- Maven assembly plugin for executable JAR
- Java 17+ configuration

✅ **Code Organization**
- Separate classes by responsibility
- Records for immutable data (DealerDetails)
- Clear separation of concerns

## 🚀 Usage

### Python

```bash
cd python

# Install
pip install -e .

# Run
python -m cardealer --input data/sample_vehicle.json --output listing.pptx
```

### Java

```bash
cd java

# Build
mvn clean package

# Run
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json \
  --output listing.pptx
```

## 📦 Key Files

### Python
- `setup.py` / `pyproject.toml`: Package configuration
- `requirements.txt`: Dependencies
- `src/cardealer/`: Main package code
- `tests/`: Unit tests

### Java
- `pom.xml`: Maven build configuration
- `src/main/java/com/cardealer/`: Main source code
- `src/test/java/com/cardealer/`: Test code

## 🔧 Development Workflow

1. **Python Development**
   ```bash
   cd python
   pip install -e .
   python -m cardealer --help
   ```

2. **Java Development**
   ```bash
   cd java
   mvn compile
   mvn test
   mvn package
   ```

## 📝 Notes

- Both implementations produce identical output
- Code is modular and maintainable
- Follows language-specific best practices
- Ready for team collaboration
- Includes comprehensive documentation

