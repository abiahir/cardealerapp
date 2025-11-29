# Car Dealer Presentation Generator

A professional tool for generating editable PowerPoint presentations from vehicle listing data. Available in both **Python** and **Java** implementations.

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Python Version](#python-version)
- [Java Version](#java-version)
- [Usage Examples](#usage-examples)
- [Development](#development)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

- 🎨 **Professional Layout**: Matches showroom presentation standards
- ✏️ **Fully Editable**: All text remains editable in PowerPoint/Google Slides
- 📊 **Structured Data**: Input from JSON files or CLI arguments
- 🔒 **Type Safety**: Validated categorical fields (fuel type, gearbox, ULEZ)
- 🚀 **Dual Implementation**: Choose Python for rapid development or Java for enterprise use
- 📦 **Well Structured**: Follows best practices for both languages

## 📁 Project Structure

```
cardealerapp/
├── python/                    # Python implementation
│   ├── src/
│   │   └── cardealer/         # Main package
│   │       ├── __init__.py
│   │       ├── cli.py         # Command-line interface
│   │       ├── constants.py   # Configuration constants
│   │       ├── models.py      # Data models
│   │       └── presentation.py # PowerPoint generation
│   ├── tests/                 # Unit tests
│   ├── data/                  # Sample data files
│   ├── requirements.txt       # Python dependencies
│   ├── setup.py              # Package setup
│   └── pyproject.toml         # Modern Python config
│
├── java/                      # Java implementation
│   ├── src/
│   │   ├── main/
│   │   │   └── java/com/cardealer/
│   │   │       ├── GeneratePpt.java
│   │   │       ├── PresentationBuilder.java
│   │   │       ├── VehicleListing.java
│   │   │       └── DealerDetails.java
│   │   └── test/              # Unit tests
│   └── pom.xml               # Maven configuration
│
├── docs/                      # Documentation
├── .gitignore
└── README.md                 # This file
```

## 🐍 Python Version

### Requirements

- Python 3.10+
- Dependencies listed in `python/requirements.txt`

### Installation

```bash
cd python
pip install -r requirements.txt

# Or install as a package
pip install -e .
```

### Usage

```bash
# From the python directory
python -m cardealer --input data/sample_vehicle.json --output listing.pptx

# Or if installed
generate-ppt --input data/sample_vehicle.json --output listing.pptx
```

See [python/README.md](python/README.md) for detailed Python documentation.

## ☕ Java Version

### Requirements

- Java 17+
- Maven 3.6+

### Building

```bash
cd java
mvn clean package
```

### Usage

```bash
# Using the executable JAR
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json \
  --output listing.pptx
```

See [java/README.md](java/README.md) for detailed Java documentation.

## 📝 Usage Examples

### Basic Usage (JSON Input)

```bash
# Python
python -m cardealer --input data/sample_vehicle.json --output vehicle.pptx

# Java
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input data/sample_vehicle.json --output vehicle.pptx
```

### Override Fields via CLI

```bash
# Python
python -m cardealer \
  --input data/sample_vehicle.json \
  --fuel-type Petrol \
  --gearbox Manual \
  --price "£15,000" \
  --output custom.pptx

# Java
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input data/sample_vehicle.json \
  --fuel-type Petrol \
  --gearbox Manual \
  --price "£15,000" \
  --output custom.pptx
```

### Create Without JSON (All CLI)

```bash
# Python
python -m cardealer \
  --title "Ford Kuga ST-Line" \
  --price "£18,500" \
  --registration "AB21 XYZ" \
  --year "2021 (21 reg)" \
  --fuel-type Hybrid \
  --gearbox Automatic \
  --engine-size "1.5 L" \
  --mileage "28,450" \
  --ulez Yes \
  --mot-expiry "15 Mar 2026" \
  --owners "1" \
  --specs "Apple CarPlay" "Android Auto" "Navigation" \
  --dealer-name "M&N Cars" \
  --dealer-phone "07446 877759" \
  --dealer-email "sales@mandncars.co.uk" \
  --dealer-website "www.mandncars.uk" \
  --output kuga.pptx
```

## 🛠️ Development

### Python Development

```bash
cd python

# Install in development mode
pip install -e .

# Run tests
pytest tests/

# Format code
black src/

# Lint code
pylint src/
```

### Java Development

```bash
cd java

# Compile
mvn compile

# Run tests
mvn test

# Build JAR
mvn package
```

## 📊 Comparison

| Feature | Python | Java |
|---------|--------|------|
| **Code Size** | ~400 lines | ~500 lines |
| **Type Safety** | Runtime | Compile-time |
| **Performance** | Good | Excellent |
| **Development Speed** | Faster | Moderate |
| **Enterprise Ready** | Good | Excellent |
| **Learning Curve** | Easier | Steeper |

Both versions produce **identical PowerPoint outputs**. Choose based on your needs:
- **Python**: Faster development, simpler code, great for scripting
- **Java**: Better for enterprise apps, type safety, team development

## 🤝 Contributing

Contributions are welcome! Please:

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

MIT License - see LICENSE file for details

## 🙏 Acknowledgments

- Built with [python-pptx](https://github.com/scanny/python-pptx) (Python)
- Built with [Apache POI](https://poi.apache.org/) (Java)
- CLI powered by [Picocli](https://picocli.info/) (Java)
