"""Command-line interface for the presentation generator."""

import argparse
import json
from pathlib import Path
from typing import Optional

from .constants import FUEL_TYPES, GEARBOX_TYPES, ULEZ_CHOICES
from .models import VehicleListing
from .presentation import PresentationBuilder


def parse_args() -> argparse.Namespace:
    """Parse command-line arguments."""
    parser = argparse.ArgumentParser(description="Generate an editable vehicle PPTX.")
    parser.add_argument("--input", type=Path, help="Path to vehicle JSON data.")
    parser.add_argument("--output", type=Path, default=Path("vehicle.pptx"), help="PPTX output file.")

    parser.add_argument("--title", help="Vehicle display title.")
    parser.add_argument("--price", help="Price string, e.g. '£12,000'.")
    parser.add_argument("--registration", help="Registration number.")
    parser.add_argument("--year", help="Year display value.")
    parser.add_argument("--gearbox", choices=GEARBOX_TYPES, help="Gearbox option.")
    parser.add_argument("--engine-size", dest="engine_size", help="Engine size label.")
    parser.add_argument("--fuel-type", dest="fuel_type", choices=FUEL_TYPES, help="Fuel type option.")
    parser.add_argument("--mileage", help="Mileage display value.")
    parser.add_argument("--ulez", choices=ULEZ_CHOICES, help="ULEZ status.")
    parser.add_argument("--mot-expiry", dest="mot_expiry", help="MOT expiry display value.")
    parser.add_argument("--owners", help="Number of owners or description.")
    parser.add_argument("--specs", nargs="*", help="List of spec bullet points.")

    parser.add_argument("--dealer-name", dest="dealer_name", help="Dealership name.")
    parser.add_argument("--dealer-phone", dest="dealer_phone", help="Dealership phone number.")
    parser.add_argument("--dealer-email", dest="dealer_email", help="Dealership email address.")
    parser.add_argument("--dealer-website", dest="dealer_website", help="Dealership website.")

    return parser.parse_args()


def load_vehicle(path: Optional[Path]) -> VehicleListing:
    """Load vehicle data from JSON file."""
    if path is None:
        return VehicleListing.from_dict({})
    data = json.loads(path.read_text())
    if not isinstance(data, dict):
        raise ValueError("Input JSON must describe a single vehicle object.")
    return VehicleListing.from_dict(data)


def main() -> None:
    """Main entry point for the CLI."""
    args = parse_args()
    vehicle = load_vehicle(args.input)
    vehicle.update_from_args(args)
    PresentationBuilder.build(vehicle, args.output)
    print(f"Saved editable PPTX to {args.output}")


if __name__ == "__main__":
    main()

