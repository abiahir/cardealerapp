"""Data models for vehicle listings and dealer information."""

from __future__ import annotations

import argparse
from dataclasses import dataclass, field
from typing import Dict, List, Optional

from .constants import FUEL_TYPES, GEARBOX_TYPES, ULEZ_CHOICES


@dataclass
class DealerDetails:
    """Represents dealer contact information."""

    name: str = "Your Dealership"
    phone: str = "0000 000 0000"
    email: str = "sales@example.com"
    website: str = "www.example.com"

    @staticmethod
    def from_dict(data: Optional[Dict[str, str]]) -> "DealerDetails":
        """Create DealerDetails from a dictionary."""
        if not data:
            return DealerDetails()
        return DealerDetails(
            name=data.get("name", "Your Dealership"),
            phone=data.get("phone", "0000 000 0000"),
            email=data.get("email", "sales@example.com"),
            website=data.get("website", "www.example.com"),
        )


@dataclass
class VehicleListing:
    """Represents a vehicle listing with all its details."""

    title: str
    price: str
    registration: str
    year: str
    gearbox: str
    engine_size: str
    fuel_type: str
    mileage: str
    ulez: str
    mot_expiry: str
    owners: str = "Unknown"
    specs: List[str] = field(default_factory=list)
    dealer: DealerDetails = field(default_factory=DealerDetails)

    @staticmethod
    def from_dict(data: Dict[str, object]) -> "VehicleListing":
        """Create VehicleListing from a dictionary."""
        def _choice(value: str, allowed: List[str], label: str) -> str:
            if value not in allowed:
                choices = ", ".join(allowed)
                raise ValueError(f"{label} must be one of: {choices}")
            return value

        return VehicleListing(
            title=str(data.get("title", "Vehicle Title")),
            price=str(data.get("price", "Price on enquiry")),
            registration=str(data.get("registration", "Registration")),
            year=str(data.get("year", "Year")),
            gearbox=_choice(str(data.get("gearbox", "Automatic")), GEARBOX_TYPES, "Gearbox"),
            engine_size=str(data.get("engine_size", "2.0 L")),
            fuel_type=_choice(str(data.get("fuel_type", "Diesel")), FUEL_TYPES, "Fuel type"),
            mileage=str(data.get("mileage", "0")),
            ulez=_choice(str(data.get("ulez", "Unknown")), ULEZ_CHOICES, "ULEZ"),
            mot_expiry=str(data.get("mot_expiry", "Unknown")),
            owners=str(data.get("owners", "Unknown")),
            specs=[str(item) for item in data.get("specs", [])],
            dealer=DealerDetails.from_dict(data.get("dealer")),
        )

    def update_from_args(self, args: argparse.Namespace) -> None:
        """Override fields with CLI arguments when provided."""
        for field_name in (
            "title",
            "price",
            "registration",
            "year",
            "gearbox",
            "engine_size",
            "fuel_type",
            "mileage",
            "ulez",
            "mot_expiry",
            "owners",
        ):
            value = getattr(args, field_name)
            if value is not None:
                setattr(self, field_name, value)

        if args.specs:
            self.specs = args.specs

        self.dealer = DealerDetails(
            name=args.dealer_name or self.dealer.name,
            phone=args.dealer_phone or self.dealer.phone,
            email=args.dealer_email or self.dealer.email,
            website=args.dealer_website or self.dealer.website,
        )

