# Car Dealer Presentation Generator - Java

Java implementation for generating editable PowerPoint presentations from vehicle listing data.

## 📋 Requirements

- Java 17 or higher
- Maven 3.6+

## 🚀 Quick Start

### Building

```bash
cd java
mvn clean package
```

This creates:
- `target/cardealerapp-1.0.0.jar` - Standard JAR
- `target/cardealerapp-1.0.0-jar-with-dependencies.jar` - Executable JAR with all dependencies

### Running

```bash
# Using the executable JAR
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json \
  --output listing.pptx
```

## 📦 Project Structure

```
java/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/cardealer/
│   │   │       ├── GeneratePpt.java          # Main CLI application
│   │   │       ├── PresentationBuilder.java   # PowerPoint generation
│   │   │       ├── VehicleListing.java        # Vehicle data model
│   │   │       └── DealerDetails.java         # Dealer information (record)
│   │   └── resources/                         # Resource files
│   └── test/
│       └── java/com/cardealer/                # Unit tests
└── pom.xml                                    # Maven configuration
```

## 💻 Usage Examples

### 1. Generate from JSON File

```bash
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json \
  --output listing.pptx
```

### 2. Override Fields from JSON

```bash
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
  --input ../python/data/sample_vehicle.json \
  --fuel-type Petrol \
  --gearbox Manual \
  --engine-size "1.6 L" \
  --owners "1" \
  --specs "Bluetooth" "Parking Sensors" \
  --output petrol-manual.pptx
```

### 3. Create Without JSON (All CLI)

```bash
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar \
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

### 4. Get Help

```bash
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar --help
```

## 📝 Available Options

### Vehicle Fields

- `--title`: Vehicle display title
- `--price`: Price string (e.g., "£12,000")
- `--registration`: Registration number
- `--year`: Year display value
- `--gearbox`: `Automatic` or `Manual`
- `--engine-size`: Engine size label
- `--fuel-type`: `Petrol`, `Diesel`, `Hybrid`, or `Electric`
- `--mileage`: Mileage display value
- `--ulez`: `Yes`, `No`, or `Unknown`
- `--mot-expiry`: MOT expiry display value
- `--owners`: Number of owners or description
- `--specs`: List of spec bullet points (can specify multiple)

### Dealer Fields

- `--dealer-name`: Dealership name
- `--dealer-phone`: Dealership phone number
- `--dealer-email`: Dealership email address
- `--dealer-website`: Dealership website

## 🧪 Development

### Compile

```bash
mvn compile
```

### Run Tests

```bash
mvn test
```

### Build JAR

```bash
mvn package
```

### Using Maven Exec Plugin

```bash
mvn exec:java -Dexec.mainClass="com.cardealer.GeneratePpt" \
  -Dexec.args="--input ../python/data/sample_vehicle.json --output listing.pptx"
```

## 📄 JSON Input Format

```json
{
  "title": "Land Rover Discovery Sport",
  "price": "£12,000",
  "registration": "KN16 ZVU",
  "year": "2016 (16 reg)",
  "gearbox": "Automatic",
  "engine_size": "2.0 L",
  "fuel_type": "Diesel",
  "mileage": "71,980",
  "ulez": "Yes",
  "mot_expiry": "19 Nov 2026",
  "owners": "2",
  "specs": ["BT", "NAV", "DAB Radio", "Rear Camera", "PX"],
  "dealer": {
    "name": "M&N Cars",
    "phone": "07446 877759",
    "email": "sales@mandncars.co.uk",
    "website": "www.mandncars.uk"
  }
}
```

## 🔧 API Usage

You can also use the classes programmatically:

```java
import com.cardealer.*;
import java.nio.file.Paths;

// Create vehicle listing
VehicleListing vehicle = VehicleListing.fromMap(vehicleData);

// Generate presentation
PresentationBuilder builder = new PresentationBuilder();
builder.buildPresentation(vehicle, Paths.get("output.pptx"));
```

## 📚 Dependencies

- **Apache POI 5.2.5**: PowerPoint file generation
- **Picocli 4.7.5**: Modern CLI parsing with autocomplete
- **Jackson 2.16.1**: JSON processing

## ✨ Features

- ✅ **Type Safety**: Compile-time checking catches errors before runtime
- ✅ **Modern CLI**: Picocli with autocomplete support
- ✅ **Enterprise Ready**: Maven build, structured codebase
- ✅ **Robust**: Exception handling and validation
- ✅ **Same Output**: Identical PowerPoint output to Python version

## 🐛 Troubleshooting

### ClassNotFoundException

Make sure you're using the `-jar-with-dependencies` JAR file:

```bash
java -jar target/cardealerapp-1.0.0-jar-with-dependencies.jar
```

### Maven Build Issues

Ensure you have Java 17+ installed:

```bash
java -version
```

### IDE Integration

This project works well with:
- IntelliJ IDEA
- Eclipse
- VS Code with Java extensions

## 📖 See Also

- [Main README](../README.md) for project overview
- [Python Version](../python/README.md) for Python implementation

