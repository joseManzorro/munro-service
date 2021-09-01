# munro-service

The munro service aims to provide an API to retrieve a munro data by category and max/min height in meters.

Data is loaded on startup from a CSV file located in the resources folder of the project.

## Installation

Using JDK 11

The file with the data should be place on:
```bash
resources/data/munro_data.csv
```
To start the service:

- From a terminal:
```bash
./gradlew bootRun
```
- From IntelliJ:
```bash
Run MunroServiceApplication class.
```

## Usage

All parameters are optional:
```python
- category:   MUN/TOP  (MUN)
- heightSort: ASC/DESC (DESC)
- nameSort:   ASC/DESC (ASC)
```

Request example:
```bash
curl http://localhost:8080/munros?category=TOP&maxHeightInMeters=2000&minHeightInMeters=900&heightSort=ASC&nameSort=DESC&limit=2
```

Response example:

```python
[
    {
        "name": "Mullach Coire nan Cisteachan [Carn na Caim South Top]",
        "heightInMeters": 914.6,
        "category": "TOP",
        "gridRef": "NN663806"
    },
    {
        "name": "Spidean a' Choire Leith (Liathach) - Stuc a' Choire Dhuibh Bhig",
        "heightInMeters": 915.0,
        "category": "TOP",
        "gridRef": "NG942582"
    }
]
```